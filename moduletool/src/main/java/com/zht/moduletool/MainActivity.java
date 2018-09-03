package com.zht.moduletool;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.modulelib.base.BaseActivity;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
public class MainActivity extends BaseActivity {


    private FrameLayout mContainer;
    private Fragment toolsFragment;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        toolsFragment = (Fragment) ARouter.getInstance()
                .build("/moduletool/toolsfragment")
                .navigation();
        //获取到FragmentManager，在V4包中通过getSupportFragmentManager，
        //在系统中原生的Fragment是通过getFragmentManager获得的。
        fragmentManager = getSupportFragmentManager();
        //开启一个事务，通过调用beginTransaction方法开启。
        fragmentTransaction = fragmentManager.beginTransaction();
        //向容器内加入Fragment，一般使用add或者replace方法实现，需要传入容器的id和Fragment的实例。
        fragmentTransaction.add(R.id.m_container,toolsFragment,"toolsFragment");
        fragmentTransaction.commit();
    }


}
