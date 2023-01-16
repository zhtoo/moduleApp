package com.zht.modulehome.activity


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zht.common.constant.ARoutePathConstants

import androidx.compose.ui.tooling.preview.Preview //使用预览功能

@Route(path = ARoutePathConstants.Home.COMPOSE_ACTIVITY)
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
                MessageCard("Hello world!")
            }
        }
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard("Android")
    }

}