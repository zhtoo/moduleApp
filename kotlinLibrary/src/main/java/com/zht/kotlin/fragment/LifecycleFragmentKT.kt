package com.zht.kotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @Date   2024/3/21 15:39
 * @Author zhanghaitao
 * @Description
 */
class LifecycleFragmentKT : Fragment() {


    private val index by lazy {
        arguments?.getInt(INDEX) ?: 0
    }

    companion object {

        const val INDEX = "index"

        fun newInstance(
            index: Int,
        ): LifecycleFragmentKT {
            val fragment = LifecycleFragmentKT()
            val bundle = Bundle()
            bundle.putInt(INDEX, index)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onAttach(context: Context) {
        Log.e("test","onAttach:$index")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("test","onCreate:$index")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.e("test","onCreateView:$index")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        Log.e("test","onStart:$index")
        super.onStart()
    }

    override fun onResume() {
        Log.e("test","onResume:$index")
        super.onResume()

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.e("test","setUserVisibleHint($isVisibleToUser):$index")
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        Log.e("test","onHiddenChanged:($hidden)->$index")
        super.onHiddenChanged(hidden)
    }

    override fun onPause() {
        Log.e("test","onPause:$index")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e("test","onSaveInstanceState:$index")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.e("test","onStop:$index")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.e("test","onDestroyView:$index")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.e("test","onDestroy:$index")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.e("test","onDetach:$index")
        super.onDetach()
    }



}