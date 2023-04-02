package com.zht.modulehome.compose.page

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @Date   2023/4/2 12:29
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ColumnPage() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.systemBarsDarkContentEnabled = true
    Text("ColumnPage")
}