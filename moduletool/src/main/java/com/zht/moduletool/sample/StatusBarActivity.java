package com.zht.moduletool.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.modulelib.StatusBar.StatusBar;
import com.zht.modulelib.base.BaseActivity;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/9/5.
 */
@Route(path = "/sample/StatusBarActivity")
public class StatusBarActivity extends BaseActivity implements View.OnClickListener {

    private boolean darkmode = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status_bar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.m_status_color).setOnClickListener(this);
        findViewById(R.id.m_transparent_status_color).setOnClickListener(this);
        findViewById(R.id.m_translucent_status_color).setOnClickListener(this);
        findViewById(R.id.m_status_text_color).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.m_status_color) {
            int color = (int) -(Math.random() * (16777216 - 1) + 1);
            StatusBar.setStatusBarColor(this, color);
        } else if (id == R.id.m_transparent_status_color) {
            StatusBar.setTransparentStatusBar(this);
        } else if (id == R.id.m_translucent_status_color) {
            StatusBar.setTranslucentStatusBar(this);
        } else if (id == R.id.m_status_text_color) {
            darkmode = !darkmode;
            StatusBar.setStatusBarLight(this, darkmode);
            Log.e("StatusBarActivity", "" + darkmode);
        }
    }
}
