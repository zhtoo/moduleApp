package com.zht.modulehome.compose.page

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zht.modulehome.compose.widget.smartRefresh
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Date   2023/4/2 13:08
 * @Author zhanghaitao
 * @Description
 */

private val TAG = "Compose"

private val uiState = mutableStateOf(DataModel())

data class DataModel(
    var list: MutableList<String> = mutableListOf<String>(),
    var inputContent: String = "",
    var refreshing: Boolean = false,
)

@Composable
fun PullRefreshPage() {
    Column(//最外层宽高设置设置无效，强制match_parent
        modifier = Modifier
            .background(Color(0xFFF3F3F3))//背景
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {

            RefreshDemoList()
        }

        Box(
            modifier = Modifier
                .height(50.dp)
                .background(Color(0xFFF0F0F0))//背景
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            TestRefreshDemoList()
        }

        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    PullRefreshPage()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RefreshDemoList() {
    var itemList by remember { mutableStateOf(mutableListOf<String>()) }
    val refreshScope = rememberCoroutineScope()
    var mRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(mRefreshing, {
        refreshScope.launch {
            mRefreshing = true
            delay(500L)
            val list = mutableListOf<String>()
            for (i in 0 until 10) {
                list.add("item:${i}")
            }
            itemList = list
            mRefreshing = false
        }
    })
    Box(
        Modifier
            .pullRefresh(state)
    ) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            items(itemList.size) { index ->
                DemoItem(itemList[index])
            }
        }
        PullRefreshIndicator(
            mRefreshing,
            state,
            Modifier.align(Alignment.TopCenter)
        )
    }
}


var offsetY: Float by mutableStateOf(0f)
var refreshing: Boolean by mutableStateOf(false)
var loading: Boolean by mutableStateOf(false)

@Composable
fun TestRefreshDemoList() {


    val list = mutableListOf<String>()
    for (i in 0 until 10) {
        list.add("test Item :${i}")
    }
    uiState.value.list = list

    var distancePulled = 0f
    var maxOffset = with(LocalDensity.current) {
        50.dp.toPx()
    }
    Box(
        Modifier
            .background(Color.Green)
            .fillMaxSize()
            .graphicsLayer {
                clip = true
            }
            .smartRefresh(
                { pullRefreshDelta ->
                    Log.e(TAG, "pullRefreshDelta: ${pullRefreshDelta}")
                    if (refreshing) {
                        0F
                    } else {
                        // 计算新的位移量
                        val newOffset = (distancePulled + pullRefreshDelta).coerceAtLeast(0f)
                        // 计算父类处理需要消费的偏移
                        val dragConsumed = newOffset - distancePulled
                        distancePulled = newOffset
                        offsetY = distancePulled
                        if (offsetY >= maxOffset) {
                            refreshing = true
                        }
                        dragConsumed
                    }
                }, { pullLoadDelta ->
                    Log.e(TAG, "pullLoadDelta: ${pullLoadDelta}")
                    if (loading) {
                        0F
                    } else {
                        // 计算新的位移量
                        val newOffset = (distancePulled + pullLoadDelta).coerceAtMost(0f)
                        // 计算父类处理需要消费的偏移
                        val dragConsumed = newOffset - distancePulled
                        distancePulled = newOffset
                        offsetY = distancePulled
                        if (offsetY <= -maxOffset) {
                            loading = true
                        }
                        dragConsumed
                    }
                },
                {
                    delay(1000L)
                    if (refreshing) {
                        refreshing = false
                    }
                    if (loading) {
                        loading = false
                    }
                    offsetY = 0f
                    distancePulled = 0f
                },
                true
            )
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = offsetY
                }
        ) {
            items(uiState.value.list.size) { index ->
                DemoItem(uiState.value.list[index])
            }
        }
        RefreshIndicator(modifier = Modifier.align(Alignment.TopCenter))
        LoadMoreIndicator(modifier = Modifier.align(Alignment.BottomCenter))
    }


}


@Composable
fun RefreshIndicator(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .graphicsLayer {
                translationY = offsetY - size.height
            },
    ) {
        Crossfade(
            targetState = refreshing,
            animationSpec = tween(durationMillis = 100), label = ""
        ) { refresh ->

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (refresh) {
                    Text(text = "刷新中...")
                } else {
                    Text(text = "下拉刷新")
                }
            }
        }
    }


}

@Composable
fun LoadMoreIndicator(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .graphicsLayer {
                translationY = offsetY + size.height
            },
    ) {
        Crossfade(
            targetState = loading,
            animationSpec = tween(durationMillis = 100), label = ""
        ) { load ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (load) {
                    Text(text = "加载中...")
                } else {
                    Text(text = "上拉加载")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
private fun onRefresh(itemList: MutableList<String>) {
    Log.e(TAG, "onRefresh: ")
    for (i in 0 until 10) {
        itemList.add("item:${i}")
    }
}

@Composable
fun DemoItem(demo: String) {
    Column(
        modifier = Modifier
            .background(Color(0x66333333))//背景
            .fillMaxWidth()
            .height(50.dp)
            .padding(15.dp)//设置内边距
    ) {
        Text(demo)
    }
}


//            Text(name, fontSize = 22.sp)
//            Text("Column demo:")
//            TextField(
//                value = uiState.value.inputContent,
//                onValueChange = { uiState.value = uiState.value.copy(inputContent = it) },
//                placeholder = { Text("请输入文字") }
//            )
//            Column(
//                modifier = Modifier
//                    .background(Color(0x66333333))//背景
//                    .size(width = 200.dp, height = 100.dp)//设置控件大小
//                    .padding(15.dp)//设置内边距
//            ) {
//                Text("Item one")
//                Text("Item two")
//            }
//            Text("Row demo:")
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .background(Color(0x66333333))
//                    .fillMaxWidth()
//            ) {
//                Text("Item one Item one Item one", fontSize = 50.sp)
//                Text("Item two")
//            }
//
//            Text("Box demo:")
//            Box(modifier = Modifier.clickable {
//
//            }) {
//                Image(
//                    painter = painterResource(id = R.mipmap.ic_jetpack_logo),
//                    contentDescription = null
//                )
//                Text("Item two")
//            }
//            Text("List demo:")

//        Canvas(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(Color(0xFFFF00FF))
//                .clip(RoundedCornerShape(16.dp))
//        ) {
////            Log.e(TAG, "size(${size.width},${size.height})" )
////            drawCircle(Color.Blue, radius = 50.dp.toPx())
//        }

//        CustomTextView(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//                .background(Color(0xFFFF00FF))
//                .clip(RoundedCornerShape(16.dp))
//        )

//        Box(
//            modifier = Modifier
//                .height(50.dp)
//                .fillMaxWidth()
//                .background(Color(0xFFFF00FF))
//        )