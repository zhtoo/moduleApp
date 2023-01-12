package com.zht.modulehome.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.R;

/**
 * @Date 2023/1/12 18:12
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.SECURITY_ACTIVITY)
public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
    }



}