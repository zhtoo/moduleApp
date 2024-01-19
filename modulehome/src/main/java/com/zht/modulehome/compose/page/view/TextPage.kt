package com.zht.modulehome.compose.page.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zht.modulehome.R

/**
 * @Date   2024/1/19 14:50
 * @Author zhanghaitao
 * @Description
 *
 * https://juejin.cn/post/7057112301446365192
 */
@Composable
fun TextPage() {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text("普通text")
        Text("字体大小：15sp", fontSize = 15.sp)
        Text("字体颜色：", color = Color(0xFFFF5A93))
        Text("字体粗细：150", fontWeight = FontWeight(800))//不同于android只有加粗
        Text("字体Cursive", fontFamily = FontFamily.Cursive)
        Text(".ttf的字体格式1234567890", fontFamily = FontFamily(Font(R.font.shandong)))
        Text("斜体字", fontStyle = FontStyle.Italic)
        Text("下划线", textDecoration = TextDecoration.Underline)
        Text("删除线", textDecoration = TextDecoration.LineThrough)
        Text("背景", modifier = Modifier.background(color = Color(0xFFFF5A93)))
        Text(
            "背景+边框",
            modifier = Modifier
                .background(color = Color(0xFFFF5A93))
                .padding(20.dp)
                .border(2.dp, color = Color.Red)
        )
        Text(
            "居右",
            modifier = Modifier
                .background(color = Color(0xFFF0F0F0))
                .fillMaxWidth()
                .height(50.dp),
            textAlign = TextAlign.Right
        )

        Text(
            "行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50",
            lineHeight = 50.sp,
            modifier = Modifier.background(color = Color(0xFFFF5A93))
        )

        Text(
            "超出裁剪：超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪",
            maxLines = 2,
            overflow = TextOverflow.Clip
        )
        Text(
            "超出省略：超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            "可点击的文字",
            modifier = Modifier
                .background(color = Color(0xFFF0F0F0))
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "可点击的文字",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
        )


        // 这里就很坑，textAlign = TextAlign.Center,只能水平居中
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            Text(
                "按钮-填充",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color(0xFFF0F0F0))
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "按钮",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
            )

        }



        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color.White)
        ) {
            Text(
                "按钮-填充",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color(0xFFF0F0F0))
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "按钮",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
            )

        }

        Text("背景", modifier = Modifier.background(color = Color(0xFFFF5A93)))

    }

}