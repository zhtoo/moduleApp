package com.zht.modulehome.compose.page.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zht.modulehome.compose.navigate.AppNavigation
import com.zht.modulehome.compose.navigate.Router
import com.zht.modulehome.compose.page.CardItem
import com.zht.modulehome.compose.page.UnknownPage

/**
 * @Date   2023/4/3 14:29
 * @Author zhanghaitao
 * @Description
 * https://developer.android.google.cn/guide/navigation/navigation-animate-transitions?hl=zh-cn
 */
@Composable
@ExperimentalAnimationApi
fun NavigatorPage() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Router.navigatorHome
    ) {
        composable(
            Router.navigatorHome,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) { NavigatorHomePage(navController) }
        composable(
            "EnterRightExitLeftPage",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            }
        ) { EnterRightExitLeftPage() }
        composable(
            "EnterBottomExitBottomPage",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                ExitTransition.None
            },
            popEnterTransition = {
                EnterTransition.None
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(250)
                )
            }
        )
        { EnterBottomExitBottomPage()}
    }
}

@Composable
fun NavigatorHomePage(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //    Text("这里主要展示的是带动画的条状，不涉及普通界面导航。")

        CardItem("右进左出") {
            navController.navigate("EnterRightExitLeftPage")
        }
        CardItem("底部进入底部退出") {
            navController.navigate("EnterBottomExitBottomPage")
        }
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

