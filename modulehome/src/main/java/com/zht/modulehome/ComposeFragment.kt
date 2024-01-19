package com.zht.modulehome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zht.common.constant.ARoutePathConstants
import com.zht.modulehome.compose.navigate.AppNavHost

@Route(path = ARoutePathConstants.Home.COMPOSE_FRAGMENT)
class ComposeFragment : Fragment {

    constructor() : super() {

    }

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
            setContent {
                AppNavHost()
            }
        }
    }

}