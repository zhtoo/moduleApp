package com.zht.modulehome.compose.navigate

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zht.modulehome.compose.page.*
import com.zht.modulehome.compose.page.animation.AnimationPage
import com.zht.modulehome.compose.page.navigator.NavigatorPage

/**
 * @Date   2023/3/31 17:36
 * @Author zhanghaitao
 * @Description AppNavHost Compose项目的主入口
 * 1、一个ComposeActivity/ComposeFragment对应就是一个Compose项目
 * 2、Compose中的界面跳转可以是Compose内部界面之间也可以是Compose与activity或fragment之间
 */
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Main",//指定开始界面
) {
    Log.e("aaa", "AppNavHost: $navController")
    Log.e("aaa", "AppNavHost: $navController.navigatorProvider")
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Log.e("aaa", "============")
//        controller.backQueue.forEach {
//            Log.e(
//                "aaa",
//                "AppNavHost backQueue: route=${it.destination.route} id=${it.destination.id}"
//            )
//        }
    }
    DisposableEffect(navController) {
        AppNavigation.setNavController(navController)
        onDispose {
            AppNavigation.clearNavController()
        }
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("Unknown") { UnknownPage() }
        composable("Main") { MainPage() }
        composable("ColumnPage") { ColumnPage() }
        composable("PullRefreshPage") { PullRefreshPage() }
        composable(Router.animationHome) { AnimationPage() }
        homeGraph()
        navigatorGraph()
    }

}

fun NavGraphBuilder.homeGraph() {
    // startDestination 和 route 的命名不能相同
    // startDestination 和 route 跳转的是同一个界面
    // 无论是使用startDestination/route进行跳转
    // 都会依次加载route和startDestination
    navigation(startDestination = "login_home", route = "login") {
        composable("login_home") { LoginPage() }
        composable("password") { PasswordPage() }
        composable("registration") { RegistrationPage() }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.navigatorGraph() {
    navigation(startDestination = Router.navigatorStart, route = Router.navigator) {
        composable(Router.navigatorStart) { NavigatorPage() }
    }
}

object Router {
    const val navigator = "navigator"
    const val navigatorStart = "navigatorStart"
    const val navigatorHome = "navigatorHome"
    const val animationHome = "animationHome"
}

@SuppressLint("StaticFieldLeak")
object AppNavigation {

    private var navController: NavHostController? = null

    /**
     * AppNavigation中navController应该与全局NavHost对应的。
     * 为防止navController被篡改，我们不希望开发者对其进行二次赋值。
     */
    fun setNavController(appNavController: NavHostController) {
        if (this.navController != null) {
            throw IllegalStateException("You can't reassign navController after assigning")
        }
        this.navController = appNavController
    }

    fun clearNavController() {
        Log.e("aaa", "clearNavController: $navController")
        this.navController = null
    }

    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        navigate(route, navOptions(builder))
    }

    fun navigate(
        route: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null,
    ) {
        val navController = checkNotNull(navController) {
            "You must init navController before calling navigate()"
        }
        try {
            navController.navigate(route, navOptions, navigatorExtras)
        } catch (e: Exception) {
            navController.navigate("Unknown")
        }
    }

    fun popBackStack(): Boolean {
        val navController = checkNotNull(navController) {
            "You must init navController before calling navigate()"
        }
        return navController.popBackStack()
    }

    fun popBackStack(
        route: String,
        inclusive: Boolean,
        saveState: Boolean = false,
    ): Boolean {
        val navController = checkNotNull(navController) {
            "You must init navController before calling navigate()"
        }
        return navController.popBackStack(route, inclusive, saveState)
    }

    @Composable
    fun interceptBackPressed(onBackPressed: () -> Unit) {
        // 拦截界面的返回事件
        val callback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        }
        val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        DisposableEffect(dispatcher) {
            dispatcher?.addCallback(callback)
            onDispose {
                callback.remove()
            }
        }
    }

}