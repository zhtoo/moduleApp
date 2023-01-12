package com.zht.modulehome.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.R;
import com.zht.modulehome.databinding.ActivityJetpackBinding;

/**
 * @Date 2023/1/12 14:35
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.COMPOSE_ACTIVITY)
public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }
}
