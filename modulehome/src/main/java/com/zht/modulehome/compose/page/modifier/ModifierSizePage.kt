package com.zht.modulehome.compose.page.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zht.modulehome.compose.styles.itemTile

/**
 * @Date   2024/2/4 17:50
 * @Author zhanghaitao
 * @Description
 */

@Composable
fun ModifierSizePage() {

    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Text("控件大小", modifier = Modifier.itemTile())

        // 指定宽高
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 50.dp)
                .background(color = Color.Gray)
        )
        // 指定 宽:使用父控件允许的最大宽度（match_parent）高50dp
        Box(
            modifier = Modifier
//                .width(100.dp) //指定宽
//                .height(100.dp) //指定高度
//                .size(width = 100.dp, height = 50.dp) //指定宽、高具体数值
//                .widthIn(min = 50.dp, max = 50.dp)//设置最大宽度和最小宽度
//                .heightIn(min = 50.dp, max = 50.dp)
//                .sizeIn(minWidth = 1.dp, maxWidth = 1.dp, minHeight = 1.dp, maxHeight = 1.dp)
//                .fillMaxWidth()
//                .fillMaxWidth()
//                .fillMaxSize()
//                .wrapContentWidth()
//                .wrapContentHeight()
//                .wrapContentSize()
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Gray)
        )

    }


}
