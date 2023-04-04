package com.zht.modulehome.compose.page

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zht.kotlin.compose.LoadMoreIndicator
import com.zht.kotlin.compose.RefreshIndicator
import com.zht.kotlin.compose.smartRefresh
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
@ExperimentalMaterialApi
fun PullRefreshPage() {
    Column(//最外层宽高设置设置无效，强制match_parent
        modifier = Modifier
            .background(Color(0xFFFFFFFF))//背景
            .padding(15.dp)
    ) {
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

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFFFF0000))
            .clip(RoundedCornerShape(16.dp))
        ) {
            Log.e(TAG, "size(${size.width},${size.height})" )
            drawCircle(Color.Blue, radius = 50.dp.toPx())
        }

        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            RefreshDemoList()
        }
        Box(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            DemoList()
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun PreviewMessageCard() {
    PullRefreshPage()
}

@Composable
@ExperimentalMaterialApi
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
        LazyColumn(Modifier.fillMaxSize()) {
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

@Composable
@ExperimentalMaterialApi
fun DemoList() {


//        Box(modifier = Modifier.smartRefresh()) {
//            LazyColumn(Modifier.fillMaxSize()) {
//                items(demoItems.size) { index ->
//                    DemoItem(demoItems[index])
//                }
//            }
//        }

//        val aaastate = rememberScrollState()
//        LaunchedEffect(Unit) { aaastate.animateScrollTo(100) }
//
    var offsetY by remember { mutableStateOf(0f) }


    val list = mutableListOf<String>()
    for (i in 0 until 10) {
        list.add("test Item :${i}")
    }
    uiState.value.list = list

    var _refreshing = false
    var _loading = false

    var distancePulled = 0f

    Box(
        Modifier
        .background(Color.Green)
        .fillMaxSize()
        .smartRefresh(
            { pullDelta ->
                if (_refreshing) {
                    0F
                } else {

                    // 计算新的位移量
                    val newOffset = (distancePulled + pullDelta).coerceAtLeast(0f)
                    // 计算父类处理需要消费的偏移
                    val dragConsumed = newOffset - distancePulled
                    distancePulled = newOffset
                    offsetY = distancePulled
                    dragConsumed
                }
            }, { pullDelta ->
                if (_loading) {
                    0F
                } else {
                    // 计算新的位移量
                    val newOffset = (distancePulled + pullDelta).coerceAtMost(0f)
                    // 计算父类处理需要消费的偏移
                    val dragConsumed = newOffset - distancePulled
                    distancePulled = newOffset
                    offsetY = distancePulled
                    dragConsumed
                }
            }, {
                offsetY = 0f
                distancePulled = 0f
            },
            true
        )
    ) {

        LazyColumn(
            Modifier
                .graphicsLayer {
                    translationY = offsetY
                }
                .fillMaxSize()
        ) {
            items(uiState.value.list.size) { index ->
                DemoItem(uiState.value.list[index])
            }
        }
        RefreshIndicator()
        LoadMoreIndicator()
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