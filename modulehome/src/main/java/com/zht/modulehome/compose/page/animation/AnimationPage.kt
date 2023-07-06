package com.zht.modulehome.compose.page.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

/**
 * @Date   2023/4/7 23:48
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun AnimationPage() {
    val alphaAnimation = remember {
        Animatable(0f)
    }
    LaunchedEffect(true) {
        alphaAnimation.animateTo(1f, tween(5000))
    }
    Box(modifier = Modifier
        .alpha(alphaAnimation.value)
        .fillMaxSize()
        .background(Color.Red)) {
        Text("AnimationPage")
    }
}