package com.zht.modulehome.compose.page.layout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zht.modulehome.compose.navigate.AppNavigation

/**
 * @Date   2023/4/2 12:29
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ColumnPage() {
    AppNavigation.getInstance().interceptBackPressed {
        Log.e("aaa", "回退键被点击")
        AppNavigation.getInstance().popBackStack()
    }
//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(Color.White)
//    systemUiController.systemBarsDarkContentEnabled = true

//    SystemBarStyle.dark(Color.WHITE)
//    SystemBarStyle.light(Color.BLACK)

//    LocalContext.current.apply {
//        if (this is ComponentActivity) {
////            enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.WHITE,Color.WHITE))
//        //            enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.BLACK ))
//
//            windowController()
//        }
//    }


    var text by rememberSaveable { mutableStateOf("ColumnPage") }

    Column(Modifier.safeDrawingPadding()) {
        Text(text, modifier = Modifier.clickable {
            text = "ColumnPage -> ok"
        })
        Button(onClick = { AppNavigation.getInstance().navigate("ColumnPage") }) {
            Text("跳转到Column界面")
        }
    }

}