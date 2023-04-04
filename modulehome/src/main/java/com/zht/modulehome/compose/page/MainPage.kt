package com.zht.modulehome.compose.page

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zht.common.R

/**
 * @Date   2023/3/31 17:59
 * @Author zhanghaitao
 * @Description
 *
 * http://www.manongjc.com/detail/27-mswuuczrkdypmri.html
 */
sealed class BottomTabItem(
    val route: String,
    val tabName: String,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
) {
    object Home : BottomTabItem(
        "Home",
        "首页",
        R.drawable.icon_home_normal,
        R.drawable.icon_home_selected
    )

    object Tools : BottomTabItem(
        "Tools",
        "工具",
        R.drawable.icon_tools_normal,
        R.drawable.icon_tools_selected
    )

    object Lib : BottomTabItem(
        "Lib",
        "库",
        R.drawable.icon_lib_normal,
        R.drawable.icon_lib_selected
    )

    object View : BottomTabItem(
        "View",
        "视图",
        R.drawable.icon_view_normal,
        R.drawable.icon_view_selected
    )

    object Personal : BottomTabItem(
        "Personal",
        "我的",
        R.drawable.icon_personal_normal,
        R.drawable.icon_personal_selected
    )
}

val items = listOf(
    BottomTabItem.Home,
    BottomTabItem.Tools,
    BottomTabItem.Lib,
    BottomTabItem.View,
    BottomTabItem.Personal,
)

@Composable
fun MainPage() {
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Log.e("aaa", "============")
//        controller.backQueue.forEach {
//            Log.e(
//                "aaa",
//                "MainPage backQueue: route=${it.destination.route} id=${it.destination.id}"
//            )
//        }
    }
    Scaffold(
        bottomBar = { BottomNavigateBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = BottomTabItem.Home.route,
            Modifier.padding(paddingValues)
        ) {
            composable("Home") { HomePage() }
            composable("Tools") { ToolsPage() }
            composable("Lib") { LibraryPage() }
            composable("View") { ViewPage() }
            composable("Personal") { PersonalPage() }
        }
    }
}

@Composable
fun BottomNavigateBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color(0xff1296DB),
                unselectedContentColor = Color(0xff707070),
                icon = {
                    Image(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        painter = painterResource(
                            id = if (selected) {
                                tab.selectedIcon
                            } else {
                                tab.unselectedIcon
                            }
                        ), contentDescription = null
                    )
                },
                label = { Text(tab.tabName) },
                onClick = {
                    if (selected) {
                        return@BottomNavigationItem
                    }
                    // 推出BackStack中最后一界面，保证BackStack中始终只用一个Item
                    navController.popBackStack()
                    // 导航到目标目的地
                    navController.navigate(tab.route)
//                    navController.navigate(tab.route) {
//                        // 原文翻译：弹出到图表的起始目的地，以避免在用户选择项目时在返回堆栈上建立大量目的地
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        // 原文翻译：重新选择同一项目时避免同一目的地的多个副本
//                        // 当重新选择Item时，避免创建新的'对象'
//                        launchSingleTop = true
//                        // 原文翻译：重新选择先前选择的项目时恢复状态
//                        restoreState = true
//                    }
                }
            )
        }
    }

}