package com.zht.modulehome.compose.page.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * @Date   2024/1/19 17:33
 * @Author zhanghaitao
 * @Description
 * https://developer.android.com/jetpack/compose/modifiers-list?hl=zh-cn
 * https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier?hl=zh-cn
 * https://jetpackcompose.cn/docs/
 */
@Composable
fun ModifierPage() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color(0xFFF0F0F0))
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "居中",
                modifier = Modifier.background(Color.Red),
            )
        }

        Spacer(Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(25.dp))
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "1111",
                modifier = Modifier.background(Color.Red),
            )
        }
        Spacer(Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(25.dp))
                .border(2.dp, color = Color(0xFFB582FF), shape = RoundedCornerShape(25.dp))
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "确定",
                color = Color.White,
            )
        }
        Spacer(Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .width(50.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(25.dp))
                .border(2.dp, color = Color.Red, shape = RoundedCornerShape(25.dp))
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "确定",
            )
        }


    }
}