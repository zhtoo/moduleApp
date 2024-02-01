package com.zht.modulehome.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zht.common.R
import com.zht.modulehome.compose.navigate.composableBottom
import com.zht.modulehome.compose.navigate.composableNormal
import com.zht.modulehome.compose.navigate.composableRight
import com.zht.modulehome.compose.page.HomePage
import com.zht.modulehome.compose.page.LibraryPage
import com.zht.modulehome.compose.page.PersonalPage
import com.zht.modulehome.compose.page.ToolsPage
import com.zht.modulehome.compose.page.ViewPage

/**
 * @Date   2023/3/31 17:59
 * @Author zhanghaitao
 * @Description
 *
 * https://www.cnblogs.com/youfat/p/15647179.html
 *
 * 这里我们也使用了NavHost，但是需要和AppNavHost做一个区分。
 * 首页使用的NavHost是控制底部tab切换page，AppNavHost是真个app生命周期内的界面跳转
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
//    navController.addOnDestinationChangedListener { controller, destination, arguments ->
////        Log.e("aaa", "============")
////        controller.backQueue.forEach {
////            Log.e(
////                "aaa",
////                "MainPage backQueue: route=${it.destination.route} id=${it.destination.id}"
////            )
////        }
//    }
    Scaffold(
        bottomBar = {
            BottomNavigateBar(navController)
        }
    ) { paddingValues ->
        // 更新版本后，modifier需要使用 .fillMaxSize()来消除切换动画
        // 猜想：没有指定NavHost的宽高会导致切换时控制器 并不知道界面大小，只有初始化该界面之后的才执行渲染
        NavHost(
            navController = navController,
            startDestination = BottomTabItem.Home.route,
            modifier = Modifier
                .padding(paddingValues)
//                .fillMaxWidth()//只使用这个，会产生上下切换效果
//                .fillMaxHeight()//只使用这个，会产生左右切换效果
                .fillMaxSize()// 使用这个会使用指定的动画
        ) {
            composable(BottomTabItem.Home.route) { HomePage() }
            composable(BottomTabItem.Tools.route) { ToolsPage() }
            composable(BottomTabItem.Lib.route) { LibraryPage() }
            composable(BottomTabItem.View.route) { ViewPage() }
            composable(BottomTabItem.Personal.route) { PersonalPage() }
        }
    }

}

@Composable
fun CustomBottomNavigateBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1.0f)
            ) {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = BottomTabItem.Home.route,
                ) {
                    composableNormal(BottomTabItem.Home.route) { HomePage() }
                    composableNormal(BottomTabItem.Tools.route) { ToolsPage() }
                    composableNormal(BottomTabItem.Lib.route) { LibraryPage() }
                    composableNormal(BottomTabItem.View.route) { ViewPage() }
                    composableNormal(BottomTabItem.Personal.route) { PersonalPage() }
                }
            }
            Surface(
                color = Color.White,
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items.forEach { tab ->
                        val selected =
                            currentDestination?.hierarchy?.any { it.route == tab.route } == true
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(weight = 1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) {
                                    if (selected) {
                                        return@clickable
                                    }
                                    // 推出BackStack中最后一界面，保证BackStack中始终只用一个Item
                                    navController.popBackStack()
                                    // 导航到目标目的地
                                    navController.navigate(tab.route)
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
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
                            Text(
                                tab.tabName, color = if (selected) {
                                    Color(0xff1296DB)
                                } else {
                                    Color(0xff707070)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigateBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = Color.White
    ) {
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