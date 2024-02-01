package com.zht.modulehome.compose.page.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
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
@ExperimentalFoundationApi
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

        // 这里就很坑，textAlign = TextAlign.Center,只能水平居中
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color(0xFF650AEC), shape = RoundedCornerShape(25.dp))
                .border(2.dp, color = Color(0xFFB582FF), shape = RoundedCornerShape(25.dp))
                .clickable {
                    Toast
                        .makeText(
                            context,
                            "点击",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
            contentAlignment = Alignment.Center,
            ) {
            Text(
                "可点击的文字",
                color = Color.White
            )
        }

        Text(
            "跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯",
            modifier = Modifier.basicMarquee()
        )

//        https://blog.csdn.net/weixin_42782922/article/details/129856291
//        https://zhuanlan.zhihu.com/p/369109654?utm_id=0
        //富文本
        val imageId = "ImageContent"
        val imageLink = "https://img2.baidu.com/it/u=1208914047,2170221461&fm=253"
        val text = buildAnnotatedString {
            appendInlineContent(imageId, imageLink)
        }
        Text(
            text = text,
            inlineContent = mapOf(
                imageId to InlineTextContent(
                    Placeholder(
                        width = 12.sp,
                        height = 12.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) { imageLink ->
//                    GlideImage(
//                        model = imageLink,
//                        contentDescription = null
//                    )
//                    Image(painter = rememberGlidePainter(request = imageLink),
//                        contentDescription = null)
                },
            )
        )


    }

}