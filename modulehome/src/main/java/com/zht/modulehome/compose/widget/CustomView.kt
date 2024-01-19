package com.zht.modulehome.compose.widget

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy

/**
 * @Date   2023/3/29 19:46
 * @Author zhanghaitao
 * @Description
 * 界面树中布置每个节点的过程分为三个步骤。每个节点必须：
 * 1、测量所有子项
 * 2、确定自己的尺寸
 * 3、放置其子项
 * 注意：Compose 界面不允许多遍测量。这意味着，布局元素不能为了尝试不同的测量配置而多次测量任何子元素。
 * 小结：节点可以是布局也可以是一个控件，但是所有的节点都必须是Layout及其子类
 */
@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // 获取到所有子项测量结果
        val placeables = measurables.map { measurable ->
            // 测量子项大小
            measurable.measure(constraints)
        }
        // 设置自己的布局大小
        layout(constraints.maxWidth, constraints.maxHeight) {
            // 设置Y轴初始值
            var yPosition = 0

            placeables.forEach { placeable ->
                // 指定子项在布局中的位置
                placeable.placeRelative(x = 0, y = yPosition)

                yPosition += placeable.height
            }
        }
    }
}


/**
 * 这里仅仅是展示自定view的流程
 * 如果没有特殊需求，建议使用Canvas类进行自定义绘制
 *
 * Compose自定义绘制主要有三种方式：
 * Modifier.drawBehind()：在drawWithContent之前调用
 * Modifier.drawWithContent()：在drawBehind之后调用
 * Box(modifier = Modifier.drawBehind{}) 或Canvas()：适用于需要处理复杂绘制的场景
 */
@Composable
@NonRestartableComposable
fun CustomTextView(
    modifier: Modifier,
) {
    Log.e("log", "CustomTextView")
    // 注意这种写法，不会被调用
//    modifier.drawWithContent {
//
//    }
    val measurePolicy = MeasurePolicy { measurables, constraints ->
        // 测量自己所需要的宽高
        Log.e("log", "CustomTextView.measure")
        with(constraints) {
            val width = if (hasFixedWidth) maxWidth else 0
            val height = if (hasFixedHeight) maxHeight else 0
            layout(width, height) {}
        }
    }
    Layout(
        modifier = modifier
            .drawBehind {
                Log.e("log", "CustomTextView.drawBehind")
                // 这里处理需要绘制的内容，
                // DrawScope内部提供了各种绘制的api
                // 当api不满足实际绘制需求时，可以使用drawIntoCanvas{} 获取canvas自己进行绘制
                drawIntoCanvas { canvas ->

                }
            }
            .drawWithContent {
                Log.e("log", "CustomTextView.drawWithContent")
            },
        measurePolicy = measurePolicy
    )
}

