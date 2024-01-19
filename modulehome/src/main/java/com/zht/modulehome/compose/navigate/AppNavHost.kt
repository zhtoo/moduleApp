package com.zht.modulehome.compose.navigate

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.zht.modulehome.compose.page.*

/**
 * @Date   2023/3/31 17:36
 * @Author zhanghaitao
 * @Description AppNavHost -> Compose项目的主入口
 * 1、一个ComposeActivity/ComposeFragment对应就是一个Compose项目
 * 2、Compose中的界面跳转可以是Compose内部界面之间也可以是Compose与activity或fragment之间，
 * 但前提是项目中支持androidx.navigation
 */
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouterPath.MAIN,//指定开始界面
) {
//    Log.e("aaa", "AppNavHost: $navController")
//    Log.e("aaa", "AppNavHost: $navController.navigatorProvider")
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
//        Log.e("aaa", "============")
//        controller.backQueue.forEach {
//            Log.e(
//                "aaa",
//                "AppNavHost backQueue: route=${it.destination.route} id=${it.destination.id}"
//            )
//        }
    }
    DisposableEffect(navController) {
        AppNavigation.getInstance().initNavController(navController)
        onDispose {
            AppNavigation.getInstance().clearNavController()
        }
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ){
        routerBuilder()
    }

}
