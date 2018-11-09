package com.zht.moduletool.sample;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.NotchScreen.MIUI;
import com.zht.common.NotchScreen.Pie;
import com.zht.common.base.BaseActivity;
import com.zht.common.util.ScreenUtils;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/10/20
 */
@Route(path = "/sample/ScreenActivity")
public class ScreenActivity extends BaseActivity {

    private TextView mText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mText = findViewById(R.id.screen_text);



        String mScreenMessage =
                "总屏幕高度(包括虚拟键):" + ScreenUtils.getTotalScreenHeight(this) + "\n" +
                        "总屏幕宽度:" + ScreenUtils.getScreenWidth(this) + "\n" +
                        "屏幕可用高度:" + ScreenUtils.getAvailableScreenHeight(this) + "\n" +
                        "状态栏高度:" + ScreenUtils.getStatusBarHeight(this) + "\n" +
                        "状态栏高度（R）:" + ScreenUtils.getStatusBarHeight1(this) + "\n" +
                        "虚拟按键的高度(未显示为0):" + ScreenUtils.getVirtualBarHeightIfRoom(this) + "\n" +
                        "虚拟按键的高度:" + ScreenUtils.getVirtualBarHeight(this) + "\n" +
                        "标题栏高度(未显示为0):" + ScreenUtils.getTitleHeight(this) + "\n" +
                        "获取屏幕宽高尺寸:" + ScreenUtils.getScreenXYInch(this).toString() + "\n" +
                        "获取屏幕尺寸:" + ScreenUtils.getScreenInch(this) + "\n" +
                        "获取屏幕真实尺寸单位PX:" + ScreenUtils.getDisplayInfomation(this).toString() + "\n" + "\n" +

                        "是否是刘海屏:" + MIUI.hasNotchInScreen(this) + "\n" +
                        "获取凹型屏的高度:" + MIUI.getNotchScreenHeight(this) + "\n" +
                        "获取凹型屏的宽度:" + MIUI.getNotchScreenWidth(this) + "\n" +
                        "「隐藏屏幕刘海」是否开启了:" + MIUI.systemHideNotchScreen(this) + "\n\n" +

                        "pie:" + Pie.hasNotchInScreen(this) + "\n" +

                        "";
        mText.setText(mScreenMessage);
    }


}
