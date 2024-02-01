package com.zht.modulehome.compose.page.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.zht.modulehome.compose.widget.CardItem
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.ComposeNavigator
import com.zht.modulehome.compose.navigate.RouterPath
import com.zht.modulehome.compose.navigate.composableBottom
import com.zht.modulehome.compose.navigate.composableNormal
import com.zht.modulehome.compose.navigate.composableRight

/**
 * @Date   2023/4/3 14:29
 * @Author zhanghaitao
 * @Description
 * https://developer.android.google.cn/guide/navigation/navigation-animate-transitions?hl=zh-cn
 */
@Composable
@ExperimentalAnimationApi
fun NavigatorPage() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        navController = navController,
        startDestination = "navigatorHome"
    ) {
        composableNormal(
            "navigatorHome"
        ) {
            NavigatorHomePage(navController)
        }
        composableNormal("EnterPage",) { EnterPage() }
        composableRight("EnterRightExitLeftPage",) { EnterRightExitLeftPage() }
        composableBottom("EnterBottomExitBottomPage") { EnterBottomExitBottomPage() }
    }
}

@Composable
fun NavigatorHomePage(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CardItem("淡入淡出") {
            navController.navigate("EnterPage")
        }
        CardItem("右进左出") {
            navController.navigate("EnterRightExitLeftPage")
        }
        CardItem("底部进入底部退出") {
            navController.navigate("EnterBottomExitBottomPage")
        }
    }
}


@Composable
fun EnterPage() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        CardItem("淡入淡出")
    }
}


@Composable
fun EnterRightExitLeftPage() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        CardItem("EnterRightExitLeftPage")
    }
}

@Composable
fun EnterBottomExitBottomPage() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        CardItem("EnterBottomExitBottomPage")
    }
}

