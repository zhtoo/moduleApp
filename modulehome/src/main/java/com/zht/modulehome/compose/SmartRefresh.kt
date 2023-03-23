package com.zht.kotlin.compose

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.zht.common.util.Logger
import kotlin.math.abs
import kotlin.math.pow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Date   2023/3/20 09:24
 * @Author zhanghaitao
 * @Description
 *  onRefresh()
 *  onLoadMore()
 *
 */
private val TAG: String = "Refresh"

/**
 * 定义一个Modifier的扩展函数，用于外层控件控制刷新/加载
 */
private fun Modifier.smartRefresh(
    onPullDown: (pullDelta: Float) -> Float,//下拉回调
    onPullUp: (pullDelta: Float) -> Float,//下拉回调
    onRelease: suspend (flingVelocity: Float) -> Unit,//手指抬起
    enabled: Boolean = true,//是否需要拦截嵌套滑动
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "smartRefresh"
    properties["onPullDown"] = onPullDown
    properties["onPullUp"] = onPullUp
    properties["onRelease"] = onRelease
    properties["enabled"] = enabled
}) {
    Modifier.nestedScroll(RefreshNestedScrollConnection(onPullDown, onPullUp, onRelease, enabled))
}

fun Modifier.smartRefresh(
    enabled: Boolean = true,
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "smartRefresh"
    properties["enabled"] = enabled
}) {
    Modifier.smartRefresh({
        Log.e(TAG, "onPullDown(available = ${it})")
        0.0F
    }, {
        Log.e(TAG, "onPullUp(available = ${it})")
        0.0F
    }, {
        Log.e(TAG, "onRelease(available = ${it})")
        0.0F
    },
        enabled
    )
}

private class RefreshNestedScrollConnection(
    private val onPullDown: (pullDelta: Float) -> Float,
    private val onPullUp: (pullDelta: Float) -> Float,
    private val onRelease: suspend (flingVelocity: Float) -> Unit,
    private val enabled: Boolean,
) : NestedScrollConnection {

    /**
     * 滑动之前
     * available:可用的滚动偏移量
     */
//    override fun onPreScroll(
//        available: Offset,
//        source: NestedScrollSource,
//    ): Offset = when {
//        !enabled -> {
//            Offset.Zero
//        }
////        source == NestedScrollSource.Drag && available.y < 0 -> Offset(
////            0f,
////            onPullUp(available.y)
////        ) // Pulling down
//        else -> Offset.Zero
//    }

    /**
     * 滑动之后
     */
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset = when {
        !enabled -> Offset.Zero
        source == NestedScrollSource.Drag && available.y > 0 -> Offset(
            0f,
            onPullDown(available.y)
        ) // Pulling Down
        source == NestedScrollSource.Drag && available.y < 0 -> Offset(
            0f,
            onPullUp(available.y)
        )// Pulling Up
        else -> Offset.Zero
    }

    //    override suspend fun onPreFling(available: Velocity): Velocity {
//        Log.e(TAG, "onPreFling(available = ${available})")
//        return Velocity.Zero
//    }
//
//    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
//        Log.e(TAG, "onPostFling(consumed = ${consumed}, available: ${available})")
//        return super.onPostFling(consumed, available)
//    }
    override suspend fun onPreFling(available: Velocity): Velocity {
        onRelease(available.y)
        return Velocity.Zero
    }

}

class SmartRefreshState internal constructor(
    private val onRefreshState: State<() -> Unit>,
    private val onLoadMoreState: State<() -> Unit>,
) {


    internal fun onPullDown(pullDelta: Float): Float {

        return 0F
    }

    internal fun onPullUp(pullDelta: Float): Float {

        return 0F
    }

    internal fun onRelease() {

    }

}

@Composable
fun RefreshIndicator(){

}

@Composable
fun LoadMoreIndicator(){

}


/**
 * -------------------------------------------------------------------------------------------------
 */


fun Modifier.pullRefresh(
    state: PullRefreshState,//状态管理
    enabled: Boolean = true,
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "pullRefresh"
    properties["state"] = state
    properties["enabled"] = enabled
}) {
    Modifier.pullRefresh(state::onPull, { state.onRelease() }, enabled)
}

fun Modifier.pullRefresh(
    onPull: (pullDelta: Float) -> Float,//下拉回调
    onRelease: suspend (flingVelocity: Float) -> Unit,//松手回调
    enabled: Boolean = true,
) = inspectable(inspectorInfo = debugInspectorInfo {
    name = "pullRefresh"
    properties["onPull"] = onPull
    properties["onRelease"] = onRelease
    properties["enabled"] = enabled
}) {
    Modifier.nestedScroll(PullRefreshNestedScrollConnection(onPull, onRelease, enabled))
}

private class PullRefreshNestedScrollConnection(
    private val onPull: (pullDelta: Float) -> Float,
    private val onRelease: suspend (flingVelocity: Float) -> Unit,
    private val enabled: Boolean,
) : NestedScrollConnection {

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource,
    ): Offset = when {
        !enabled -> Offset.Zero
        source == NestedScrollSource.Drag && available.y < 0 -> Offset(
            0f,
            onPull(available.y)
        ) // Swiping up
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset = when {
        !enabled -> Offset.Zero
        source == NestedScrollSource.Drag && available.y > 0 -> Offset(
            0f,
            onPull(available.y)
        ) // Pulling down
        else -> Offset.Zero
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        onRelease(available.y)
        return Velocity.Zero
    }
}


@Composable
fun rememberPullRefreshState(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    refreshThreshold: Dp = PullRefreshDefaults.RefreshThreshold,
    refreshingOffset: Dp = PullRefreshDefaults.RefreshingOffset,
): PullRefreshState {
    require(refreshThreshold > 0.dp) { "The refresh trigger must be greater than zero!" }

    val scope = rememberCoroutineScope()
    val onRefreshState = rememberUpdatedState(onRefresh)
    val thresholdPx: Float
    val refreshingOffsetPx: Float

    with(LocalDensity.current) {
        thresholdPx = refreshThreshold.toPx()
        refreshingOffsetPx = refreshingOffset.toPx()
    }

    // refreshThreshold and refreshingOffset should not be changed after instantiation, so any
    // changes to these values are ignored.
    val state = remember(scope) {
        PullRefreshState(scope, onRefreshState, refreshingOffsetPx, thresholdPx)
    }

    SideEffect {
        state.setRefreshing(refreshing)
    }

    return state
}


class PullRefreshState internal constructor(
    private val animationScope: CoroutineScope,
    private val onRefreshState: State<() -> Unit>,
    private val refreshingOffset: Float,
    internal val threshold: Float,
) {

    val progress get() = adjustedDistancePulled / threshold

    internal val refreshing get() = _refreshing
    internal val position get() = _position

    private val adjustedDistancePulled by derivedStateOf { distancePulled * DragMultiplier }

    private var _refreshing by mutableStateOf(false)
    private var _position by mutableStateOf(0f)
    private var distancePulled by mutableStateOf(0f)

    internal fun onPull(pullDelta: Float): Float {
        if (this._refreshing) return 0f // Already refreshing, do nothing.

        val newOffset = (distancePulled + pullDelta).coerceAtLeast(0f)
        val dragConsumed = newOffset - distancePulled
        distancePulled = newOffset
        _position = calculateIndicatorPosition()
        return dragConsumed
    }

    internal fun onRelease() {
        if (!this._refreshing) {
            if (adjustedDistancePulled > threshold) {
                onRefreshState.value()
            } else {
                animateIndicatorTo(0f)
            }
        }
        distancePulled = 0f
    }

    internal fun setRefreshing(refreshing: Boolean) {
        if (this._refreshing != refreshing) {
            this._refreshing = refreshing
            this.distancePulled = 0f
            animateIndicatorTo(if (refreshing) refreshingOffset else 0f)
        }
    }

    private fun animateIndicatorTo(offset: Float) = animationScope.launch {
        animate(initialValue = _position, targetValue = offset) { value, _ ->
            _position = value
        }
    }

    private fun calculateIndicatorPosition(): Float = when {
        // If drag hasn't gone past the threshold, the position is the adjustedDistancePulled.
        adjustedDistancePulled <= threshold -> adjustedDistancePulled
        else -> {
            // How far beyond the threshold pull has gone, as a percentage of the threshold.
            val overshootPercent = abs(progress) - 1.0f
            // Limit the overshoot to 200%. Linear between 0 and 200.
            val linearTension = overshootPercent.coerceIn(0f, 2f)
            // Non-linear tension. Increases with linearTension, but at a decreasing rate.
            val tensionPercent = linearTension - linearTension.pow(2) / 4
            // The additional offset beyond the threshold.
            val extraOffset = threshold * tensionPercent
            threshold + extraOffset
        }
    }
}

object PullRefreshDefaults {

    val RefreshThreshold = 80.dp

    val RefreshingOffset = 56.dp
}

private const val DragMultiplier = 0.5f
