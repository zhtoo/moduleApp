package com.zht.modulehome.activity

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zht.common.base.BaseViewBindingActivity
import com.zht.common.constant.ARoutePathConstants
import com.zht.modulehome.databinding.ActivityJetpackBinding

/**
 * @Date 2023/1/12 14:35
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.JETPACK_ACTIVITY)
class JetpackActivity : BaseViewBindingActivity<ActivityJetpackBinding?>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.tvTest.setOnClickListener {
            startActivity(
                Intent(
                    this@JetpackActivity,
                    ComposeActivity::class.java
                )
            )
        }
    }
}