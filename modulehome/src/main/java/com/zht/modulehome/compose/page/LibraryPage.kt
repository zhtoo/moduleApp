package com.zht.modulehome.compose.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.zht.modulehome.compose.navigate.AppNavigation

/**
 * @Date   2023/3/31 17:51
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun LibraryPage() {
    var text by rememberSaveable { mutableStateOf("LibraryPage") }
    Column(modifier = Modifier.safeDrawingPadding()) {
        Text(text, modifier = Modifier.clickable {
            text = "LibraryPage -> ok"
        })
        Button(onClick = { AppNavigation.getInstance().navigate("Main") }) {
            Text("跳转到Main界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("ColumnPage") }) {
            Text("跳转到Column界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("PullRefreshPage") }) {
            Text("跳转到PullRefresh界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("login") }) {
            Text("跳转到login界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("login_home") }) {
            Text("跳转到login_home界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("password") }) {
            Text("跳转到password界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("registration") }) {
            Text("跳转到registration界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("Unknown") }) {
            Text("跳转到Unknown界面")
        }
        Button(onClick = { AppNavigation.getInstance().navigate("Unkaaanown") }) {
            Text("跳转到Unkaaanown界面")
        }
    }

}