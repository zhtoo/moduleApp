package com.zht.moduleview.activity.customise;

import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2019/5/27
 * TouchDrag
 */
@Route(path = "/moduleview/activity/DragActivity")
public class DragActivity extends BaseActivity {

    private static final String TAG = "DragActivity";

    private ImageView mDragBt;


    @Override
    protected int getLayoutId() {
        return R.layout.drag_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mDragBt = findViewById(R.id.drag_bt);


    }





}
