package com.zht.modulehome.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.zht.kotlin.compose.*
import com.zht.modulehome.compose.navigate.AppNavHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  modifier.fillMaxSize() 宽高fill
 *  modifier.fillMaxWidth()() 宽fill
 *  modifier.fillMaxHeight()()() 高fill
 *
 */
class ComposeFragment : Fragment() {



    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return createComposeView(requireContext())
    }

    @ExperimentalMaterialApi
    private fun createComposeView(context: Context): View {
        return ComposeView(context).apply {
            clipChildren = true
            setContent {
                AppNavHost()
                //MessageCard("Hello Android !!!")
            }
        }
    }

//    val mutableState = remember { mutableStateOf(default) }
//    var value by remember { mutableStateOf(default) }
//    val (value, setValue) = remember { mutableStateOf(default) }



}