package com.zht.modulehome.compose.page

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zht.modulehome.compose.navigate.AppNavigation
import com.zht.modulehome.compose.navigate.Router

/**
 * @Date   2023/3/31 17:47
 * @Author zhanghaitao
 * @Description
 */
@SuppressLint("RememberReturnType")
@Composable
fun HomePage() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    systemUiController.systemBarsDarkContentEnabled = true

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
                AppNavigation.navigate(Router.navigator)
            }
            CardItem("动画") {
                AppNavigation.navigate(Router.animationHome)
            }
            for (i in 0 until 10) {
                CardItem("demo${i}") {

                }
            }
        }

    }




}


/**

app:cardCornerRadius="10dp"
app:cardElevation="10dp"
 */

@Composable
fun CardItem(name: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 7.5.dp, 15.dp, 7.5.dp)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) { onClick() },
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(name, textAlign = TextAlign.Center)
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
