package com.zht.modulehome.compose.navigate

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zht.modulehome.compose.page.layout.ColumnPage
import com.zht.modulehome.compose.page.LoginPage
import com.zht.modulehome.compose.MainPage
import com.zht.modulehome.compose.page.PasswordPage
import com.zht.modulehome.compose.page.refresh.PullRefreshPage
import com.zht.modulehome.compose.page.RegistrationPage
import com.zht.modulehome.compose.UnknownPage
import com.zht.modulehome.compose.page.ViewPage
import com.zht.modulehome.compose.page.animation.AnimationPage
import com.zht.modulehome.compose.page.navigator.NavigatorPage
import com.zht.modulehome.compose.page.status_bar.StatusBarPage
import com.zht.modulehome.compose.page.modifier.ModifierOtherPage
import com.zht.modulehome.compose.page.modifier.ModifierTransformationsPage
import com.zht.modulehome.compose.page.modifier.ModifierActionsPage
import com.zht.modulehome.compose.page.modifier.ModifierAlignmentPage
import com.zht.modulehome.compose.page.modifier.ModifierAnimationPage
import com.zht.modulehome.compose.page.modifier.ModifierBorderPage
import com.zht.modulehome.compose.page.modifier.ModifierDrawingPage
import com.zht.modulehome.compose.page.modifier.ModifierFocusPage
import com.zht.modulehome.compose.page.modifier.ModifierGraphicsPage
import com.zht.modulehome.compose.page.modifier.ModifierKeyboardPage
import com.zht.modulehome.compose.page.modifier.ModifierLayoutPage
import com.zht.modulehome.compose.page.modifier.ModifierPaddingPage
import com.zht.modulehome.compose.page.modifier.ModifierPointerPage
import com.zht.modulehome.compose.page.modifier.ModifierPositionPage
import com.zht.modulehome.compose.page.modifier.ModifierScrollPage
import com.zht.modulehome.compose.page.modifier.ModifierSemanticsPage
import com.zht.modulehome.compose.page.modifier.ModifierSizePage
import com.zht.modulehome.compose.page.modifier.ModifierTestingPage

import com.zht.modulehome.compose.page.view.ModifierPage
import com.zht.modulehome.compose.page.view.TextPage

/**
 * @Date   2024/1/19 14:52
 * @Author zhanghaitao
 * @Description
 */
object RouterPath {

    const val UNKNOWN = "Unknown"
    const val MAIN = "Main"
    const val COLUMN_PAGE = "ColumnPage"
    const val PULL_REFRESH_PAGE = "PullRefreshPage"

    const val VIEW_PAGE = "viewPage"
    const val STATUS_BAR_PAGE = "statusBarPage"
    const val TEXT_PAGE = "textPage"
    const val MODIFIER_PAGE = "modifierPage"

    const val MODIFIER_ACTIONS_PAGE = "modifierActionsPage"
    const val MODIFIER_ALIGNMENT_PAGE = "modifierAlignmentPage"
    const val MODIFIER_ANIMATION_PAGE = "modifierAnimationPage"
    const val MODIFIER_BORDER_PAGE = "modifierBorderPage"
    const val MODIFIER_DRAWING_PAGE = "modifierDrawingPage"
    const val MODIFIER_FOCUS_PAGE = "modifierFocusPage"
    const val MODIFIER_GRAPHICS_PAGE = "modifierGraphicsPage"
    const val MODIFIER_KEYBOARD_PAGE = "modifierKeyboardPage"
    const val MODIFIER_LAYOUT_PAGE = "modifierLayoutPage"
    const val MODIFIER_PADDING_PAGE = "modifierPaddingPage"
    const val MODIFIER_POINTER_PAGE = "modifierPointerPage"
    const val MODIFIER_POSITION_PAGE = "modifierPositionPage"
    const val MODIFIER_SEMANTICS_PAGE = "modifierSemanticsPage"
    const val MODIFIER_SCROLL_PAGE = "modifierScrollPage"
    const val MODIFIER_SIZE_PAGE = "modifierSizePage"
    const val MODIFIER_TESTING_PAGE = "modifierTestingPage"
    const val MODIFIER_TRANSFORMATIONS_PAGE = "modifierTransformationsPage"
    const val MODIFIER_OTHER_PAGE = "modifierOtherPage"

    const val navigator = "navigator"
    const val navigatorStart = "navigatorStart"
    const val animationHome = "animationHome"

}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.routerBuilder() {
    composable(RouterPath.UNKNOWN) { UnknownPage() }
    composable(RouterPath.MAIN) { MainPage() }
    composable(RouterPath.animationHome) { AnimationPage() }
    homeGraph()
    navigatorGraph()
    viewGraph()
}

@ExperimentalFoundationApi
fun NavGraphBuilder.viewGraph() {
    navigation(startDestination = RouterPath.VIEW_PAGE, route = "view") {
        composable(RouterPath.VIEW_PAGE) { ViewPage() }
        composable(RouterPath.STATUS_BAR_PAGE) { StatusBarPage() }
        composable(RouterPath.TEXT_PAGE) { TextPage() }
        composable(RouterPath.MODIFIER_PAGE) { ModifierPage() }

        composableNormal(RouterPath.MODIFIER_ACTIONS_PAGE) { ModifierActionsPage() }
        composableNormal(RouterPath.MODIFIER_ALIGNMENT_PAGE) { ModifierAlignmentPage() }
        composableNormal(RouterPath.MODIFIER_ANIMATION_PAGE) { ModifierAnimationPage() }
        composableNormal(RouterPath.MODIFIER_BORDER_PAGE) { ModifierBorderPage() }
        composableNormal(RouterPath.MODIFIER_DRAWING_PAGE) { ModifierDrawingPage() }
        composableNormal(RouterPath.MODIFIER_FOCUS_PAGE) { ModifierFocusPage() }
        composableNormal(RouterPath.MODIFIER_GRAPHICS_PAGE) { ModifierGraphicsPage() }
        composableNormal(RouterPath.MODIFIER_KEYBOARD_PAGE) { ModifierKeyboardPage() }
        composableNormal(RouterPath.MODIFIER_LAYOUT_PAGE) { ModifierLayoutPage() }
        composableNormal(RouterPath.MODIFIER_PADDING_PAGE) { ModifierPaddingPage() }
        composableNormal(RouterPath.MODIFIER_POINTER_PAGE) { ModifierPointerPage() }
        composableNormal(RouterPath.MODIFIER_POSITION_PAGE) { ModifierPositionPage() }
        composableNormal(RouterPath.MODIFIER_SEMANTICS_PAGE) { ModifierSemanticsPage() }
        composableNormal(RouterPath.MODIFIER_SCROLL_PAGE) { ModifierScrollPage() }
        composableNormal(RouterPath.MODIFIER_SIZE_PAGE) { ModifierSizePage() }
        composableNormal(RouterPath.MODIFIER_TESTING_PAGE) { ModifierTestingPage() }
        composableNormal(RouterPath.MODIFIER_TRANSFORMATIONS_PAGE) { ModifierTransformationsPage() }
        composableNormal(RouterPath.MODIFIER_OTHER_PAGE) { ModifierOtherPage() }

        composable(RouterPath.COLUMN_PAGE) { ColumnPage() }
        composable(RouterPath.PULL_REFRESH_PAGE) { PullRefreshPage() }
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
    navigation(startDestination = RouterPath.navigatorStart, route = RouterPath.navigator) {
        composable(RouterPath.navigatorStart) { NavigatorPage() }
    }
}


const val DEFAULT_DURATION = 200

fun NavGraphBuilder.composableNormal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = {
            fadeIn(animationSpec = tween(DEFAULT_DURATION))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(DEFAULT_DURATION))
        }
    )

}

fun NavGraphBuilder.composableRight(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DEFAULT_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DEFAULT_DURATION)
            )
        }
    )
}

fun NavGraphBuilder.composableBottom(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(DEFAULT_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(DEFAULT_DURATION)
            )
        }
    )
}
