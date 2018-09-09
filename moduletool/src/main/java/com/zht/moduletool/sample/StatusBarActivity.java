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
    private boolean isVisibility = true;
    private boolean isFullScreen = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status_bar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.m_status_color).setOnClickListener(this);
        findViewById(R.id.m_status_text_color).setOnClickListener(this);
        findViewById(R.id.m_status_visibility).setOnClickListener(this);
        findViewById(R.id.m_full_screen).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.m_status_color) {
            int color = (int) -(Math.random() * (16777216 - 1) + 1);
            StatusBar.setStatusBarColor(this, color);
        } else if (id == R.id.m_status_text_color) {
            darkmode = !darkmode;
            StatusBar.setStatusBarLight(this, darkmode);
        } else if (id == R.id.m_status_visibility) {
            isVisibility = !isVisibility;
            StatusBar.setStatusBarVisibility(this, isVisibility);
        }else if (id == R.id.m_full_screen) {
            isFullScreen = !isFullScreen;
            StatusBar.setFullScreen(this, isFullScreen);
        }
    }
}
