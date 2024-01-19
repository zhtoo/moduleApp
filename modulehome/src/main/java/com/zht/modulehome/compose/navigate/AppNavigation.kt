package com.zht.modulehome.compose.navigate

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.navOptions

/**
 * @Date   2024/1/19 15:16
 * @Author zhanghaitao
 * @Description
 */
@SuppressLint("StaticFieldLeak")
class AppNavigation {

    private var navController: NavHostController? = null

    companion object {

        val appNavigation by lazy {
            AppNavigation()
        }

        fun getInstance(): AppNavigation {
            return this.appNavigation
        }

    }

    /**
     * AppNavigation中navController应该与全局NavHost对应的。
     * 为防止navController被篡改，我们不希望开发者对其进行二次赋值。
     */
    fun initNavController(appNavController: NavHostController) {
        if (this.navController != null) {
            throw IllegalStateException("You can't reassign navController after assigning")
        }
        this.navController = appNavController
    }

    fun clearNavController() {
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
            e.printStackTrace()
            navController.navigate(RouterPath.UNKNOWN)
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