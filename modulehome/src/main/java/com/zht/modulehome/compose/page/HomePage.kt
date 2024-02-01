package com.zht.modulehome.compose.page

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zht.modulehome.compose.navigate.AppNavigation
import com.zht.modulehome.compose.navigate.RouterPath
import com.zht.modulehome.compose.widget.CardItem

/**
 * @Date   2023/3/31 17:47
 * @Author zhanghaitao
 * @Description
 */
@SuppressLint("RememberReturnType")
@Composable
fun HomePage() {
//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(Color.White)
//    systemUiController.systemBarsDarkContentEnabled = true

    var text by rememberSaveable { mutableStateOf("HomePage") }

    Column( Modifier
        .fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    text = "HomePage -> ok"
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text, textAlign = TextAlign.Center)
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CardItem("带动画导航") {
                AppNavigation.getInstance().navigate(RouterPath.navigator)
            }
            CardItem("动画") {
                AppNavigation.getInstance().navigate(RouterPath.animationHome)
            }

            CardItem("状态栏") {
                AppNavigation.getInstance().navigate(RouterPath.STATUS_BAR_PAGE)
            }
            for (i in 0 until 10) {
                CardItem("demo${i}") {

                }
            }
        }

    }

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
