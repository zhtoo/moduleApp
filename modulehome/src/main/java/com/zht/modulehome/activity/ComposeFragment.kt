package com.zht.modulehome.activity


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.zht.modulehome.R

class ComposeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return createComposeView(requireContext())
    }

    private fun createComposeView(context: Context): View {
        return ComposeView(context).apply {
            setContent {
                MessageCard("Android!")
            }
        }
    }

    @Composable
    fun MessageCard(name: String) {
        Column(//最外层宽高设置设置无效，强制match_parent
            modifier = Modifier
                .background(Color(0xFF1296DB))//背景
                .padding(15.dp)
        ) {
            Text("Column demo:")
            Column(
                modifier = Modifier
                    .background(Color(0xFF333333))//背景
                    .size(width = 200.dp, height = 100.dp)//设置控件大小
                    .padding(15.dp)//设置内边距
            ) {
                Text("Item one")
                Text("Item two")
            }
            Text("Row demo:")
            Row {
                Text("Item one")
                Text("Item two")
            }

            Text("Box demo:")
            Box {
                Image(
                    painter = painterResource(id = R.mipmap.ic_jetpack_logo),
                    contentDescription = null
                )
                Text("Item two")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard("Android")
    }

}