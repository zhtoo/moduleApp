package com.zht.modulehome.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.zht.modulehome.R

/**
 *  modifier.fillMaxSize() 宽高fill
 *  modifier.fillMaxWidth()() 宽fill
 *  modifier.fillMaxHeight()()() 高fill
 *
 */
class ComposeFragment : Fragment() {

    private val TAG = "Compose"

    private val uiState = mutableStateOf(DataModel())

    data class DataModel(
        val list: MutableList<String> = mutableListOf<String>(),
        var inputContent: String = "",
        var refreshing: Boolean = false,
    )


    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return createComposeView(requireContext())
    }

    @ExperimentalMaterialApi
    private fun createComposeView(context: Context): View {
        return ComposeView(context).apply {
            setContent {
                MessageCard("Hello Android !!!")
            }
        }
    }

//    val mutableState = remember { mutableStateOf(default) }
//    var value by remember { mutableStateOf(default) }
//    val (value, setValue) = remember { mutableStateOf(default) }

    @Composable
    @ExperimentalMaterialApi
    fun MessageCard(name: String) {
        Column(//最外层宽高设置设置无效，强制match_parent
            modifier = Modifier
                .background(Color(0xFFFFFFFF))//背景
                .padding(15.dp)
        ) {
            Text(name, fontSize = 22.sp)
            Text("Column demo:")
            TextField(
                value = uiState.value.inputContent,
                onValueChange = { uiState.value = uiState.value.copy(inputContent = it) },
                placeholder = { Text("请输入文字") }
            )
            Column(
                modifier = Modifier
                    .background(Color(0x66333333))//背景
                    .size(width = 200.dp, height = 100.dp)//设置控件大小
                    .padding(15.dp)//设置内边距
            ) {
                Text("Item one")
                Text("Item two")
            }
            Text("Row demo:")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0x66333333))
                    .fillMaxWidth()
            ) {
                Text("Item one Item one Item one", fontSize = 50.sp)
                Text("Item two")
            }

            Text("Box demo:")
            Box(modifier = Modifier.clickable {

            }) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_jetpack_logo),
                    contentDescription = null
                )
                Text("Item two")
            }
            Text("List demo:")
            DemoList(uiState.value.list)
        }
    }

    @Preview
    @Composable
    @ExperimentalMaterialApi
    fun PreviewMessageCard() {
        MessageCard("Android")
    }

    @Composable
    @ExperimentalMaterialApi
    fun DemoList(demoItems: List<String>) {
//        Column {
//            Text("刷新", modifier = Modifier
//                .height(50.dp)
//                .fillMaxWidth())
//            LazyColumn {
//                items(demoItems.size) { index ->
//                    DemoItem(demoItems[index])
//                }
//            }
//            Text("加载", modifier = Modifier
//                .height(50.dp)
//                .fillMaxWidth())
//        }
        val state = rememberPullRefreshState(uiState.value.refreshing, { onRefresh() })
        Box(Modifier.pullRefresh(state)) {
            LazyColumn (Modifier.fillMaxSize()){
                items(demoItems.size) { index ->
                    DemoItem(demoItems[index])
                }
            }
            PullRefreshIndicator(
                uiState.value.refreshing,
                state,
                Modifier.align(Alignment.TopCenter)
            )
        }

    }

    private fun onRefresh() {
        uiState.value.refreshing = true
        val list = mutableListOf<String>()
        for (i in 0 until 10) {
            list.add("item:${i}")
        }
        uiState.value = uiState.value.copy(list = list, refreshing = false)
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

}