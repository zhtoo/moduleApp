package com.zht.kotlin.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @Date   2023/1/5 16:14
 * @Author zhanghaitao
 * @Description
 */
class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    var mViewModel: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actualTypeArgument = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        mViewModel = ViewModelProvider(this)[actualTypeArgument as Class<T>]

    }

}