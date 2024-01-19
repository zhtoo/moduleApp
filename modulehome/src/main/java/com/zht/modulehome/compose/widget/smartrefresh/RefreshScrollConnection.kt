package com.zht.modulehome.compose.widget.smartrefresh

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

/**
 * @Date   2024/1/18 15:47
 * @Author zhanghaitao
 * @Description
 */
class RefreshScrollConnection(
    private val onPullDown: (pullDelta: Float) -> Float,
    private val onPullUp: (pullDelta: Float) -> Float,
    private val onRelease: suspend (flingVelocity: Float) -> Unit,
    private val enabled: Boolean,
) : NestedScrollConnection {

    /**
     * onPreScroll：滑动之前
     * available:可用的滚动偏移量
     */
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return super.onPreScroll(available, source)
    }

    /**
     * onPostScroll：滑动之后
     * consumed: 所有子类已经消费了的滑动距离
     * available: 父类可以处理的滑动距离
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

    override suspend fun onPreFling(available: Velocity): Velocity {
        onRelease(available.y)
        return Velocity.Zero
    }

}