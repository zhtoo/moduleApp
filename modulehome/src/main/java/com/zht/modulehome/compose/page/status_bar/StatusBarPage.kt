package com.zht.modulehome.compose.page.status_bar

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * @Date   2024/1/22 17:59
 * @Author zhanghaitao
 * @Description
 * https://developer.android.google.cn/develop/ui/views/layout/insets
 * https://developer.android.google.cn/develop/ui/views/layout/edge-to-edge
 */

const val FULL_SCREEN = 1
const val USE_STATUS_BAR = 2
const val USE_NAV_BAR = 3
const val NO_FULL_SCREEN = 4


val list = mutableStateOf(mutableListOf<String>())


@Composable
fun StatusBarPage() {

//  val windowInsetsController =
//        WindowCompat.getInsetsController(window, window.decorView)
//
//// Hide the system bars.
//    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
//
//// Show the system bars.
//    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())


    val context = LocalContext.current.apply {
        if (this is ComponentActivity) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
    }
    val windowInsetsController = if (context is Activity)
        WindowCompat.getInsetsController(context.window, context.window.decorView) else null

    if (list.value.isEmpty()) {
        Log.e("log", "isEmpty: ")
        for (index in 0..20) {
            list.value.add("")
        }
    }

    var status by rememberSaveable { mutableStateOf(USE_STATUS_BAR) }
    Box(
        modifier = when (status) {
            FULL_SCREEN -> Modifier.padding(0.dp)
            USE_STATUS_BAR -> Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            USE_NAV_BAR -> Modifier.windowInsetsPadding(WindowInsets.statusBars)
            NO_FULL_SCREEN -> Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
            else -> Modifier.background(Color(0xffa4ddb0))
        }
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffa4ddb0))
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        status = FULL_SCREEN
                    },
                    textAlign = TextAlign.Center,
                    text = "全屏"
                )
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        status = USE_STATUS_BAR
                    },
                    textAlign = TextAlign.Center,
                    text = "使用状态栏空间"
                )
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        status = USE_NAV_BAR
                    },
                    textAlign = TextAlign.Center,
                    text = "使用导航栏空间"
                )
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        status = NO_FULL_SCREEN
                    },
                    textAlign = TextAlign.Center,
                    text = "不使用全屏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "隐藏状态栏(仅仅隐藏状态栏，状态栏空间不可用)"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.show(WindowInsetsCompat.Type.statusBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "显示状态栏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.hide(WindowInsetsCompat.Type.navigationBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "隐藏导航栏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.show(WindowInsetsCompat.Type.navigationBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "显示导航栏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "隐藏状态栏和导航栏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        windowInsetsController?.show(WindowInsetsCompat.Type.systemBars())
                    },
                    textAlign = TextAlign.Center,
                    text = "显示状态栏和导航栏"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        if (context is ComponentActivity) {
                            context.enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.auto(
                                    android.graphics.Color.TRANSPARENT,
                                    android.graphics.Color.TRANSPARENT
                                ) {
                                    false
                                }
                            )
                        }

                    },
                    textAlign = TextAlign.Center,
                    text = "透明状态栏黑色字体"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        if (context is ComponentActivity) {
                            context.enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.auto(
                                    android.graphics.Color.TRANSPARENT,
                                    android.graphics.Color.TRANSPARENT
                                ) {
                                    true
                                }
                            )
                        }

                    },
                    textAlign = TextAlign.Center,
                    text = "透明状态栏白色字体"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        if (context is ComponentActivity) {
                            context.enableEdgeToEdge(
                                navigationBarStyle = SystemBarStyle.auto(
                                    android.graphics.Color.TRANSPARENT,
                                    android.graphics.Color.TRANSPARENT
                                ) {
                                    false
                                }
                            )
                        }

                    },
                    textAlign = TextAlign.Center,
                    text = "透明导航栏黑色字体"
                )

                Text(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        if (context is ComponentActivity) {
                            context.enableEdgeToEdge(
                                navigationBarStyle = SystemBarStyle.auto(
                                    android.graphics.Color.TRANSPARENT,
                                    android.graphics.Color.TRANSPARENT
                                ) {
                                    true
                                }
                            )
                        }

                    },
                    textAlign = TextAlign.Center,
                    text = "透明导航栏白色字体"
                )
            }

            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "我是顶"
            )
            Text(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = "我是底"
            )

//            LazyColumn(
//                Modifier
//                    .fillMaxSize()
////                    .imePadding()
//            ) {
//                items(list.value.size) { index ->
//                    TextField(
//                        value = list.value[index],
//                        onValueChange = {
//                            Log.e("log", "${it}")
//                            list.value.apply {
//                                this[index] = it
//                            }.also {
//                                Log.e("log", "???")
//                                list.value = it
//                            }
//                        },
//                        placeholder = { Text("请输入文字") }
//                    )
//                }
//            }
        }

    }
}