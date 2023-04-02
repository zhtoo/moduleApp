package com.zht.modulehome.compose.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.zht.modulehome.compose.navigate.AppNavigation

/**
 * @Date   2023/3/31 17:51
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun LibraryPage() {
    Column(){
        var text by remember { mutableStateOf("LibraryPage") }
        Text(text, modifier = Modifier.clickable {
            text = "LibraryPage -> ok"
        })
        Button(onClick = { AppNavigation.navigate("Main") }) {
            Text("跳转到Main界面")
        }
        Button(onClick = { AppNavigation.navigate("ColumnPage") }) {
            Text("跳转到Column界面")
        }
        Button(onClick = { AppNavigation.navigate("PullRefreshPage") }) {
            Text("跳转到PullRefresh界面")
        }
        Button(onClick = { AppNavigation.navigate("login") }) {
            Text("跳转到login界面")
        }
        Button(onClick = { AppNavigation.navigate("login_home") }) {
            Text("跳转到login_home界面")
        }
        Button(onClick = { AppNavigation.navigate("password") }) {
            Text("跳转到password界面")
        }
        Button(onClick = { AppNavigation.navigate("registration") }) {
            Text("跳转到registration界面")
        }
        Button(onClick = { AppNavigation.navigate("Unknown") }) {
            Text("跳转到Unknown界面")
        }
        Button(onClick = { AppNavigation.navigate("Unkaaanown") }) {
            Text("跳转到Unkaaanown界面")
        }
    }

}