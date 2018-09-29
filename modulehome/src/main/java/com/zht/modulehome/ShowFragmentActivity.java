package com.zht.modulehome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseActivity;

import butterknife.OnClick;


public class ShowFragmentActivity extends BaseActivity {

    private Fragment fragmentModuleOneShow;
    private Fragment fragmentMmoduleTwoShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        fragmentModuleOneShow = (Fragment) ARouter.getInstance()
                .build("/moduleone/showfragment")
                .navigation();
        fragmentMmoduleTwoShow = (Fragment) ARouter.getInstance()
                .build("/moduletwo/showfragment")
                .navigation();

        findViewById(R.id.tv_moduleone_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(fragmentModuleOneShow).hide(fragmentMmoduleTwoShow).commit();
            }
        });
        findViewById(R.id.tv_moduletwo_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(fragmentMmoduleTwoShow).hide(fragmentModuleOneShow).commit();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(R.id.fl_fragment,fragmentModuleOneShow);
        beginTransaction.add(R.id.fl_fragment,fragmentMmoduleTwoShow);
        beginTransaction.show(fragmentModuleOneShow).hide(fragmentMmoduleTwoShow).commit();
    }

}
