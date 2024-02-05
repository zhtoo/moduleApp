package com.zht.modulehome.compose.page.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zht.modulehome.compose.styles.itemTile

/**
 * @Date   2024/2/4 17:46
 * @Author zhanghaitao
 * @Description
 */
@Composable
fun ModifierAlignmentPage() {
    // TODO: alignBy 未研究清楚
    //  Text("1", modifier = Modifier
    //      .alignBy(VerticalAlignmentLine { a, b ->
    //          Log.e("bbb", "(${a},${b})" )
    //          100
    //      })
    //      .background(Color.Gray))
    //  Text("1", modifier = Modifier
    //      .alignBy {
    //          Log.e("aaa", "(${it})" )
    //          Log.e("aaa", "(${it.measuredWidth},${it.measuredHeight})" )
    //          it.measuredWidth
    //      }
    //      .background(Color.Gray))
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Text("Box", modifier = Modifier.itemTile())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(150.dp)
        ) {
            Text("左上", modifier = Modifier.align(Alignment.TopStart))
            Text("顶部居中", modifier = Modifier.align(Alignment.TopCenter))
            Text("右上", modifier = Modifier.align(Alignment.TopEnd))
            Text("靠左居中", modifier = Modifier.align(Alignment.CenterStart))
            Text("居中", modifier = Modifier.align(Alignment.Center))
            Text("靠右居中", modifier = Modifier.align(Alignment.CenterEnd))
            Text("左下", modifier = Modifier.align(Alignment.BottomStart))
            Text("底部居中", modifier = Modifier.align(Alignment.BottomCenter))
            Text("右下", modifier = Modifier.align(Alignment.BottomEnd))
        }


        Text("Column", modifier = Modifier.itemTile())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(
                "左", modifier = Modifier
                    .align(Alignment.Start)
                    .background(Color.Gray)
            )
            Text(
                "居中", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(
                        Color.Gray
                    )
            )
            Text(
                "右", modifier = Modifier
                    .align(Alignment.End)
                    .background(Color.Gray)
            )

        }

        Text("Row", modifier = Modifier.itemTile())
        Row(
            modifier = Modifier
                .height(150.dp)
                .background(Color.White)
        ) {
            Text(
                "上", modifier = Modifier
                    .align(Alignment.Top)
                    .background(Color.Gray)
            )
            Text(
                "居中", modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(Color.Gray)
            )
            Text(
                "下", modifier = Modifier
                    .align(Alignment.Bottom)
                    .background(Color.Gray)
            )
        }
    }

}
