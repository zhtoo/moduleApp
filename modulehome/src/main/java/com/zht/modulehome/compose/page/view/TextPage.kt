package com.zht.modulehome.compose.page.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
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
import androidx.compose.ui.util.fastFilter
import com.zht.modulehome.R
import com.zht.modulehome.compose.styles.itemTile
import com.zht.modulehome.compose.widget.CardItem
import kotlinx.coroutines.coroutineScope

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
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        CardItem {
            Text("普通text")
        }
        CardItem {
            Text("字体大小：15sp", fontSize = 15.sp)
        }
        CardItem {
            Text("字体颜色：", color = Color(0xFFFF5A93))
        }
        CardItem {
            Text("字体粗细：150", fontWeight = FontWeight(800))//不同于android只有加粗
        }
        CardItem {
            Text("字体Cursive", fontFamily = FontFamily.Cursive)
        }
        CardItem {
            Text(".ttf的字体格式1234567890", fontFamily = FontFamily(Font(R.font.shandong)))
        }
        CardItem {
            Text("斜体字", fontStyle = FontStyle.Italic)
        }
        CardItem {
            Text("下划线", textDecoration = TextDecoration.Underline)
        }
        CardItem {
            Text("删除线", textDecoration = TextDecoration.LineThrough)
        }
        CardItem {
            Text("背景", modifier = Modifier.background(color = Color(0xFFFF5A93)))
        }
        CardItem {
            Text(
                "居右",
                modifier = Modifier
                    .background(color = Color(0xFFF0F0F0))
                    .fillMaxWidth()
                    .height(50.dp),
                textAlign = TextAlign.Right
            )
        }

        CardItem {
            Text(
                "行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50行高50",
                lineHeight = 50.sp,
                modifier = Modifier.background(color = Color(0xFFFF5A93))
            )
        }

        CardItem {
            Text(
                "超出裁剪：超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪",
                maxLines = 2,
                overflow = TextOverflow.Clip
            )
        }

        CardItem {
            Text(
                "超出省略：超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪超出裁剪",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        CardItem {
            // 这里就很坑，textAlign = TextAlign.Center,只能水平居中
            Box(
                modifier = Modifier
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
        }

        CardItem {
            Text(
                "跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯跑马灯",
                modifier = Modifier.basicMarquee()
            )

        }

        CardItem {

            SelectionContainer {
                Text(
                    "我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本我是可选择文本"
                )
            }
        }

        CardItem {
            val annotatedString = AnnotatedString(text = "我是可点击文案")
            ClickableText(text = annotatedString, onClick = {
                Toast
                    .makeText(
                        context,
                        "点击了->${it}",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            })
        }
        Text("富文本", modifier = Modifier.itemTile())
        CardItem {
            val richString =
                "富文本->粗体、斜体、下划线、删除线、字体颜色、字体大小、字体背景、字体Cursive"

            val staticRichString = AnnotatedString(
                text = richString,
                spanStyles = listOf<AnnotatedString.Range<SpanStyle>>(
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("粗体"),
                        end = richString.indexOf("粗体") + 2,
                        item = SpanStyle(
                            fontWeight = FontWeight(1000)
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("斜体"),
                        end = richString.indexOf("斜体") + 2, item = SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("下划线"),
                        end = richString.indexOf("下划线") + 3,
                        item = SpanStyle(
                            textDecoration = TextDecoration.Underline
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("删除线"),
                        end = richString.indexOf("删除线") + 3, item = SpanStyle(
                            textDecoration = TextDecoration.LineThrough
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("字体颜色"),
                        end = richString.indexOf("字体颜色") + 4,
                        item = SpanStyle(
                            color = Color.Red
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("字体大小"),
                        end = richString.indexOf("字体大小") + 4, item = SpanStyle(
                            fontSize = 20.sp
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("字体背景"),
                        end = richString.indexOf("字体背景") + 4, item = SpanStyle(
                            background = Color(0xFFFF5A93)
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = richString.indexOf("字体Cursive"),
                        end = richString.indexOf("字体Cursive") + 9, item = SpanStyle(
                            fontFamily = FontFamily.Cursive
                        )
                    ),
                )
            )
            Text(staticRichString)
        }
//
        CardItem {
            //appendInlineContent(id: String, alternateText: String)
            //pushStringAnnotation(tag: String, annotation: String)
            //pushStyle(style: SpanStyle)
            //append(text: String)
            val imageKey = "ImageContent"
            val imageAlternate = "https://img2.baidu.com/it/u=1208914047,2170221461&fm=253"
            val text = buildAnnotatedString {
                append("这里展示文本中插入")
                appendInlineContent(imageKey, imageAlternate)
                append("图片")
            }
            Text(
                text = text,
                inlineContent = mapOf(
                    imageKey to InlineTextContent(
                        Placeholder(
                            width = 24.sp,
                            height = 24.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                        )
                    ) { imageAlternate ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.mipmap.ic_compose_logo),
                            contentDescription = null
                        )
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

        Text(
            modifier = Modifier.itemTile(),
            text = "可点击的文字:\n" +
                    "ClickableText实现方式会导致整个text都是可点击的，不可点击的文字也会响应点击，只不过我们在onClick中没有处理\n" +
                    "pointerInput实现方式只有可点击部分的文字才会消费点击事件，处理过程更加灵活，但是对应的开发者需要做额外的逻辑处理\n" +
                    "在实际的开发过程中，出现ClickableText不可点击的文字的点击区域与其他控件触控事件的情况很少。一般来说使用的ClickableText的开发效率更高"
        )

        CardItem {
            val clickableString = "ClickableText 实现可点击->点击这里看看是不是可以点"
            val staticClickableString = AnnotatedString(
                text = clickableString,
                spanStyles = listOf<AnnotatedString.Range<SpanStyle>>(
                    AnnotatedString.Range<SpanStyle>(
                        start = clickableString.indexOf("点击这里"),
                        end = clickableString.indexOf("点击这里") + 4,
                        item = SpanStyle(
                            color = Color.Red
                        )
                    ),
                )
            )
            ClickableText(text = staticClickableString, onClick = {
                val startIndex = clickableString.indexOf("点击这里")
                if (it in startIndex..(startIndex + 4)) {
                    Toast
                        .makeText(
                            context,
                            "点击这里",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            })
        }


        CardItem {
            val pointerClickableString = "Pointer 实现可点击->点击这里看看是不是可以点《用户协议》"
            val staticPointerClickableString = AnnotatedString(
                text = pointerClickableString,
                spanStyles = listOf<AnnotatedString.Range<SpanStyle>>(
                    AnnotatedString.Range<SpanStyle>(
                        start = pointerClickableString.indexOf("点击这里"),
                        end = pointerClickableString.indexOf("点击这里") + 4,
                        item = SpanStyle(
                            color = Color.Red
                        )
                    ),
                    AnnotatedString.Range<SpanStyle>(
                        start = pointerClickableString.indexOf("《用户协议》"),
                        end = pointerClickableString.indexOf("《用户协议》") + 6,
                        item = SpanStyle(
                            color = Color.Blue
                        )
                    ),
                )
            )
            val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
            val dealClick = remember { mutableStateOf<AnnotatedString.Range<SpanStyle>?>(null) }
            Text(
                modifier = Modifier.pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown().also {
                            layoutResult.value?.getOffsetForPosition(it.position)
                                ?.let { clickPosition ->
                                    if (clickPosition > -1) {
                                        dealClick.value =
                                            staticPointerClickableString.spanStyles.fastFilter {
                                                clickPosition in it.start..it.end
                                            }.firstOrNull()
                                    }
                                }
                            if (dealClick.value != null) {
                                it.consume()
                            }
                        }
                        waitForUpOrCancellation()?.also {
                            if (dealClick.value != null) {
                                it.consume()
                                Toast.makeText(
                                    context,
                                    pointerClickableString.subSequence(
                                        dealClick.value!!.start,
                                        dealClick.value!!.end,
                                    ),
                                    Toast.LENGTH_SHORT
                                ).show()
                                dealClick.value = null
                            }
                        }

                    }
                },
                text = staticPointerClickableString,
                onTextLayout = {
                    layoutResult.value = it
                },
            )
        }


//        https://blog.csdn.net/weixin_42782922/article/details/129856291
//        https://zhuanlan.zhihu.com/p/369109654?utm_id=0
        //富文本
        // 这里富文本有两种使用方式
        // 1、定义全文，给全文添加span格式。适用于已知数据的情况
        // 2、拼接span格式。适用于需要根据请求数据拼接数据


    }

}