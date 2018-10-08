package com.zht.moduleview.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2018/9/30.
 */
@Route(path = "/moduleview/activity/CustomiseViewActivity")
public class CustomiseViewActivity extends BaseActivity {

    private View mRecycler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customise_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_customise_rv);
    }

}
