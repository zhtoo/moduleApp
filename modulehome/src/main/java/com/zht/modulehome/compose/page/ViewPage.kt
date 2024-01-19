package com.zht.modulehome.compose.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zht.modulehome.compose.navigate.AppNavigation
import com.zht.modulehome.compose.navigate.RouterPath
import com.zht.modulehome.compose.widget.CardItem

/**
 * @Date   2023/3/31 17:51
 * @Author zhanghaitao
 * @Description
 * https://m3.material.io/components
 */
@Composable
fun ViewPage() {
    Column(
        Modifier
            .fillMaxSize()
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CardItem("modifier") {
                AppNavigation.getInstance().navigate(RouterPath.MODIFIER_PAGE)
            }
            CardItem("Text") {
                AppNavigation.getInstance().navigate(RouterPath.TEXT_PAGE)
            }


        }

    }
}