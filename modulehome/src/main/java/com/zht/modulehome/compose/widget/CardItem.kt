package com.zht.modulehome.compose.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * @Date   2024/1/19 16:00
 * @Author zhanghaitao
 * @Description
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
fun CardItem(
    alignment: Alignment = Alignment.CenterStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 7.5.dp, 15.dp, 7.5.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .defaultMinSize(minHeight = 60.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                contentAlignment = alignment,
                content = content
            )
        }
    }
}