package com.zht.modulehome.compose.navigate

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zht.modulehome.compose.page.*

/**
 * @Date   2023/3/31 17:36
 * @Author zhanghaitao
 * @Description
 */
@ExperimentalMaterialApi
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Main",//指定开始界面
) {
    AppNavigation.navController = navController
    Log.e("aaa", "AppNavHost: " + navController)
    Log.e("aaa", "AppNavHost: " + navController.navigatorProvider)
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Log.e("aaa", "============")
        controller.backQueue.forEach {
            Log.e(
                "aaa",
                "AppNavHost backQueue: route=${it.destination.route} id=${it.destination.id}"
            )
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
        homeGraph(navController)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
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

@SuppressLint("StaticFieldLeak")
object AppNavigation {

    var navController: NavHostController? = null

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
        try{
            navController.navigate(route, navOptions, navigatorExtras)
        }catch (e:Exception){
            navController.navigate("Unknown")
        }
    }

}