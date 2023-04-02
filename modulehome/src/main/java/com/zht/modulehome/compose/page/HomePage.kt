package com.zht.modulehome.compose.page

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @Date   2023/3/31 17:47
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun HomePage() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.systemBarsDarkContentEnabled = true
    var text by remember { mutableStateOf("HomePage") }
    Text(text, modifier = Modifier.clickable {
        text = "HomePage -> ok"
    })
}


@Composable
fun LoginPage() {
    Text("LoginPage")
}

@Composable
fun PasswordPage() {
    Text("PasswordPage")
}

@Composable
fun RegistrationPage() {
    Text("RegistrationPage")
}
