package com.zht.kotlin.lifecycle

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Date   2023/1/5 15:49
 * @Author zhanghaitao
 * @Description
 */
class BaseViewModel : ViewModel() {

    val mutable = MutableLiveData<Long>()
    val liveDataMerger = MediatorLiveData<Long>()

    init {
        // 添加对mutable数据变化的监听，当
        liveDataMerger.addSource(mutable){
            liveDataMerger.value = it
        }
    }


}