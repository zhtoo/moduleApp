package com.zht.modulehome.compose

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

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
    ){measurables, constraints ->
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