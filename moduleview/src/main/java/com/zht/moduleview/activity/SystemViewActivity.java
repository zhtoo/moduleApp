package com.zht.moduleview.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2018/9/30.
 */
@Route (path = "/moduleview/activity/SystemViewActivity")
public class SystemViewActivity extends BaseActivity {

    private RecyclerView mRecycler;

    @Override
    protected int getLayoutId() {
        return com.zht.common.R.layout.activity_system_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_system_rv);
    }
}
