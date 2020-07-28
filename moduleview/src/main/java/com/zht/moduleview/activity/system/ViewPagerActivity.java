package com.zht.moduleview.activity.system;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2018/12/21
 */
@Route(path = "/moduleview/activity/ViewPagerActivity")
public class ViewPagerActivity extends BaseActivity {


    private ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.view_pager_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mViewPager = findViewById(R.id.ViewPager);

    }



}



