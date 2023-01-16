package com.zht.modulehome.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.zht.common.base.BaseViewBindingActivity
import com.zht.common.constant.ARoutePathConstants
import com.zht.modulehome.R
import com.zht.modulehome.databinding.ActivityComposeBinding


/**
 * @Date 2023/1/12 14:35
 * @Author zhanghaitao
 * @Description 不能用 Java 编写 Jetpack Compose 组件。
 * Jetpack Compose 大量使用 Kotlin 功能，例如协程，
 * 并且@Composable方法需要由 Kotlin 编译器插件完成的转换。没有办法从 Java 访问这些。
 *
 * 将项目升级为7.0+
 * gradle插件也需要升级为7.0+
 * 在对应的app模块中开启compose
 */
@Route(path = ARoutePathConstants.Home.COMPOSE_ACTIVITY)
class ComposeActivity : BaseViewBindingActivity<ActivityComposeBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
//         supportFragmentManager?.apply {
//             this.beginTransaction()?.apply {
//                 add( R.id.compose_container,ComposeFragment(),"ComposeFragment")
//                 commitAllowingStateLoss()
//                 executePendingTransactions()
//             }
//         }
    }


}