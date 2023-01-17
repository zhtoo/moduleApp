package com.zht.modulehome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.databinding.ActivityJetpackBinding;

/**
 * @Date 2023/1/12 14:35
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.JETPACK_ACTIVITY)
public class JetpackActivity extends BaseViewBindingActivity<ActivityJetpackBinding> {


    @Override
    protected void initView(Bundle savedInstanceState) {
         binding.tvTest.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(JetpackActivity.this,ComposeActivity.class));
             }
         });
    }
}
