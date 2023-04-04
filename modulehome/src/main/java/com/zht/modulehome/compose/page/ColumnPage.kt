package com.zht.modulehome.compose.page

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zht.modulehome.compose.navigate.AppNavigation

/**
 * @Date   2023/4/2 12:29
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ColumnPage() {
    AppNavigation.interceptBackPressed {
        Log.e("aaa", "回退键被点击")
        AppNavigation.popBackStack()
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.systemBarsDarkContentEnabled = true

    var text by rememberSaveable { mutableStateOf("ColumnPage") }

    Column() {
        Text(text, modifier = Modifier.clickable {
            text = "ColumnPage -> ok"
        })
        Button(onClick = { AppNavigation.navigate("ColumnPage") }) {
            Text("跳转到Column界面")
        }
    }

}