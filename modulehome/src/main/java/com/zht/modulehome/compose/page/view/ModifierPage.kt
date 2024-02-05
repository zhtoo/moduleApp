package com.zht.modulehome.compose.page.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zht.modulehome.compose.navigate.AppNavigation
import com.zht.modulehome.compose.navigate.RouterPath
import com.zht.modulehome.compose.widget.CardItem

/**
 * @Date   2024/1/19 17:33
 * @Author zhanghaitao
 * @Description
 * https://developer.android.com/jetpack/compose/modifiers-list?hl=zh-cn
 * https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier?hl=zh-cn
 * https://jetpackcompose.cn/docs/
 *
 * Actions
 * Alignment
 * Animation
 * Border
 * Drawing
 * Focus
 * Graphics
 * Keyboard
 * Layout
 * Padding
 * Pointer
 * Position
 * Semantics
 * Scroll
 * Size
 * Testing
 * Transformations
 * Other
 */
@Composable
fun ModifierPage() {


    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        CardItem("Actions(触控事件)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_ACTIONS_PAGE)
        }
        CardItem("Alignment(对齐方式)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_ALIGNMENT_PAGE)
        }
        CardItem("Animation(动画)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_ANIMATION_PAGE)
        }
        CardItem("Border(边框)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_BORDER_PAGE)
        }
        CardItem("Drawing(绘制)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_DRAWING_PAGE)
        }
        CardItem("Focus(焦点)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_FOCUS_PAGE)
        }
        CardItem("Graphics") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_GRAPHICS_PAGE)
        }
        CardItem("Keyboard(键盘)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_KEYBOARD_PAGE)
        }
        CardItem("Layout(布局)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_LAYOUT_PAGE)
        }
        CardItem("Padding(内边距)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_PADDING_PAGE)
        }
        CardItem("Pointer()") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_POINTER_PAGE)
        }
        CardItem("Position(位置)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_POSITION_PAGE)
        }
        CardItem("Semantics(语意)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_SEMANTICS_PAGE)
        }
        CardItem("Scroll(滚动)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_SCROLL_PAGE)
        }
        CardItem("Size(大小)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_SIZE_PAGE)
        }
        CardItem("Testing()") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_TESTING_PAGE)
        }
        CardItem("Transformations(变换)") {
            AppNavigation.getInstance().navigate(RouterPath.MODIFIER_TRANSFORMATIONS_PAGE)
        }
        CardItem("Other()") { AppNavigation.getInstance().navigate(RouterPath.MODIFIER_OTHER_PAGE) }
    }


//    val context = LocalContext.current
//    Column(
//        modifier = Modifier
//            .safeDrawingPadding()
//            .fillMaxSize()
//            .padding(15.dp)
//            .background(Color(0xFFF0F0F0))
//            .verticalScroll(rememberScrollState())
//    ) {
//
//
//
//        Text("Box对齐方式", modifier = Modifier.itemTile())
//
//
//
//        Text("绘制背景", modifier = Modifier.itemTile())
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(color = Color(0xFFFFFFFF)),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("白色背景", color = Color.White)
//        }
//
////
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(25.dp))
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(25.dp))
//                .border(2.dp, color = Color(0xFFB582FF), shape = RoundedCornerShape(25.dp))
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("带边框圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            Color(0xFFFF4C4C),
//                            Color(0xFF6B1EFF),
//                        )
//                    ),
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("渐变背景", color = Color.White)
//        }
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(
//                    width = 2.dp,
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            Color(0xFFFF4C4C),
//                            Color(0xFF6B1EFF),
//                        )
//                    ),
//                    shape = RectangleShape
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("渐变边框", color = Color.White)
//        }
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .width(50.dp)
//                .background(color = Color.Black, shape = RoundedCornerShape(25.dp))
//                .border(2.dp, color = Color.Red, shape = RoundedCornerShape(25.dp))
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                "确定", color = Color.White
//            )
//        }
//
//
//    }
}


























//Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(topStart = 25.dp))
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(topEnd = 25.dp))
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFF650AEC),
//                    shape = RoundedCornerShape(bottomEnd = 25.dp)
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFF650AEC),
//                    shape = RoundedCornerShape(bottomStart = 25.dp)
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFF650AEC),
//                    shape = RoundedCornerShape(bottomStart = 25.dp)
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFF650AEC),
//                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }
//
//        Spacer(Modifier.height(15.dp))
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color(0xFF650AEC),
//                    shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
//                )
//                .height(50.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("圆角背景", color = Color.White)
//        }