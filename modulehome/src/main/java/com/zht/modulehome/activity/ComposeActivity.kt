package com.zht.modulehome.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zht.common.base.BaseViewBindingActivity
import com.zht.common.constant.ARoutePathConstants
import com.zht.modulehome.R
import com.zht.modulehome.databinding.ActivityComposeBinding

/**
 * @Date 2023/1/17 10:29
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.COMPOSE_ACTIVITY)
class ComposeActivity : BaseViewBindingActivity<ActivityComposeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager?.apply {
            this.beginTransaction()?.apply {
                add(R.id.compose_container, ComposeFragment(), "ComposeFragment")
                commitAllowingStateLoss()
                executePendingTransactions()
            }
        }
    }

}