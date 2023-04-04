package com.zht.modulehome.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.zht.kotlin.compose.*
import com.zht.modulehome.compose.navigate.AppNavHost

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
           // Navigation.findNavController(this)
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