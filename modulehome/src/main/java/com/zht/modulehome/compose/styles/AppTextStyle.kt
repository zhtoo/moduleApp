package com.zht.modulehome.compose.styles

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Date   2024/2/4 15:15
 * @Author zhanghaitao
 * @Description
 */
/**
 * 定义一个Modifier的扩展函数，用于外层控件控制刷新/加载
 */
fun Modifier.itemTile(): Modifier = this then padding(15.dp, 7.5.dp, 15.dp, 7.5.dp)
