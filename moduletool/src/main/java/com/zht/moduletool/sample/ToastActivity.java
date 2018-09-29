package com.zht.moduletool.sample;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.common.util.ToastUtil;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/9/5.
 */
@Route (path = "/sample/ToastActivity")
public class ToastActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        findViewById(R.id.m_button).setOnClickListener(this);
        findViewById(R.id.m_button_center).setOnClickListener(this);
        findViewById(R.id.m_button_top).setOnClickListener(this);
        findViewById(R.id.m_button_long).setOnClickListener(this);
        findViewById(R.id.m_button_center_long).setOnClickListener(this);
        findViewById(R.id.m_button_top_long).setOnClickListener(this);
        findViewById(R.id.m_button_top_icon).setOnClickListener(this);
        findViewById(R.id.m_button_myself).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        toastClick(view);
    }

    public void toastClick(View view) {
        int id = view.getId();
        if (id == R.id.m_button) {
            ToastUtil.showToast("showToast");
        } else if (id == R.id.m_button_center) {
            ToastUtil.showToastCenter("showToastCenter");
        } else if (id == R.id.m_button_top) {
            ToastUtil.showToastTop("showToastTop");
        } else if (id == R.id.m_button_long) {
            ToastUtil.showLongToast("showLongToast");
        } else if (id == R.id.m_button_center_long) {
            ToastUtil.showLongToastCenter("showLongToastCenter");
        } else if (id == R.id.m_button_top_long) {
            ToastUtil.showLongToastTop("showLongToastTop");
        } else if (id == R.id.m_button_top_icon) {
            ToastUtil.showImageToast("showImageToast", R.mipmap.ic_launcher);
        } else if (id == R.id.m_button_myself) {
            ToastUtil.showCustomerToast("showCustomerToast");
        }
    }


}
