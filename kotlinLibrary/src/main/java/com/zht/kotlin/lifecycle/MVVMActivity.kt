package com.zht.kotlin.lifecycle

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @Date   2023/1/5 16:14
 * @Author zhanghaitao
 * @Description
 * 从MVVM设计思路来说，view <=> ViewModel <=> Model
 * view 是不需要直接和model进行交互
 */
abstract class MVVMActivity<T : BaseViewModel, V : ViewDataBinding> : AppCompatActivity() {

    var mViewModel: T? = null
    var mBinding: V? = null
    val mBindingParams = SparseArray<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化ViewModel，因为后续数据绑定需要ViewModel实例对象，所以ViewModel的初始化需要在绑定视图之前
        val actualTypeArgument =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        mViewModel = ViewModelProvider(this)[actualTypeArgument as Class<T>]

        //绑定视图
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding?.apply {
            lifecycleOwner = this@MVVMActivity
            for (i in 0 until mBindingParams.size()) {
                setVariable(mBindingParams.keyAt(i), mBindingParams.valueAt(i))
            }
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun bindData()

}