package com.zht.modulehome.compose.page.modifier

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * @Date   2024/2/4 18:15
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ModifierActionsPage() {
    val density = LocalDensity.current

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        //
        AnchoredDraggableBox()
    }
}


enum class DragAnchors {
    Start,
    Center,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnchoredDraggableBox() {
    val density = LocalDensity.current
    val anchors = DraggableAnchors {
        DragAnchors.Start at with(density) { -50.dp.toPx() }
        DragAnchors.Center at 0f
        DragAnchors.End at with(density) { 50.dp.toPx() }
    }
    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = anchors,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 56.dp.toPx() } },
            animationSpec = tween()
        )
    }

    SideEffect {
        state.updateAnchors(anchors)
    }
    Box(
        Modifier
            .size(200.dp)
            .background(Color.White)
            .offset {
                IntOffset(
                    x = state
                        .requireOffset()
                        .roundToInt(), y = 0
                )
            }
            .anchoredDraggable(state = state, orientation = Orientation.Horizontal),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .size(50.dp)
                .background(Color.Red)
        )
    }
}


