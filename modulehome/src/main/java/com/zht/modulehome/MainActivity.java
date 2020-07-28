package com.zht.modulehome;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseActivity;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
public class MainActivity extends BaseActivity {

    private Fragment homeFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        homeFragment = (Fragment) ARouter.getInstance()
                .build("/modulehome/homefragment")
                .navigation();
        //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
        //在系统中原生的Fragment是通过getFragmentManager获得的。

        fragmentManager = getSupportFragmentManager();
        //开启一个事务，通过调用beginTransaction方法开启。
        fragmentTransaction = fragmentManager.beginTransaction();
        //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
        fragmentTransaction.add(R.id.m_container, homeFragment,"toolsFragment");
        fragmentTransaction.commit();
    }


}
