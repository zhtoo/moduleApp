package com.zht.modulehome.compose.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @Date   2023/3/31 17:51
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ToolsPage() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Black)
    systemUiController.systemBarsDarkContentEnabled = false
    Text("ToolsPage")
}