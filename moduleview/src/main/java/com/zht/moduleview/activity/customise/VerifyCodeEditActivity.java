package com.zht.moduleview.activity.customise;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2019/4/4
 */
@Route(path = "/moduleview/activity/VerifyCodeEditActivity")
public class VerifyCodeEditActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.verify_code_edit_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        View mVerify = findViewById(R.id.VerifyCodeEditText);
        mVerify.setFocusable(true);
        mVerify.setFocusableInTouchMode(true);
        mVerify.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mVerify, 0);

    }
}
