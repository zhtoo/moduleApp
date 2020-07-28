package com.zht.moduletool.sample;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.NotchScreen.NotchScreen;
import com.zht.common.NotchScreen.Pie;
import com.zht.common.base.BaseActivity;
import com.zht.common.util.ScreenUtils;
import com.zht.moduletool.R;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/10/20
 */
@Route(path = "/sample/ScreenActivity")
public class ScreenActivity extends BaseActivity {
    private static final String TAG = "ScreenActivity";

    private TextView mText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        "获取屏幕真实尺寸单位PX:" + ScreenUtils.getDisplayInfomation(this).toString() + "\n"
                        + "\n";
        mText.setText(mScreenMessage);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    String s = mText.getText().toString();


                    List<Rect> notchInScreenSize = Pie.getNotchScreenSize(ScreenActivity.this);

//                    for (Rect rect : notchInScreenSize) {
//                        int left = rect.left;
//                        int top = rect.top;
//                        int right = rect.right;
//                        int bottom = rect.bottom;
//                        Log.e(TAG, "Left:" + left);
//                        Log.e(TAG, "Top:" + top);
//                        Log.e(TAG, "Right:" + right);
//                        Log.e(TAG, "Bottom:" + bottom);
//                    }

                    int width = 0;
                    int height = 0;

                    if (notchInScreenSize != null && notchInScreenSize.size() > 0) {
                        Rect rect = notchInScreenSize.get(0);
                        width = rect.right - rect.left;
                        height = rect.bottom - rect.top;
                    }
                    s += "是否是Android Pie:true" + "\n" +
                            "系统:" + NotchScreen.getOS() + "\n" +
                            "是否是刘海屏:" + Pie.hasNotchInScreen(ScreenActivity.this) + "\n" +
                            "获取凹型屏的宽度:" + width + "\n" +
                            "获取凹型屏的高度:" + height + "\n"+
                            "「隐藏屏幕刘海」是否开启了:" + NotchScreen.systemHideNotchScreen(ScreenActivity.this) + "\n"
                    ;
                    mText.setText(s);
                    Log.e(TAG, "getResult: " + System.currentTimeMillis());
                }
            });
        } else {
            String s = mText.getText().toString();
            mText.setText(s +
                    "是否是Android Pie:flase" + "\n" +
                    "系统:" + NotchScreen.getOS() + "\n" +
                    "是否是刘海屏:" + NotchScreen.hasNotchScreen(this) + "\n" +
                    "获取凹型屏的宽度:" + NotchScreen.getNotchSize(this)[0] + "\n" +
                    "获取凹型屏的高度:" + NotchScreen.getNotchSize(this)[1] + "\n" +
                    "「隐藏屏幕刘海」是否开启了:" + NotchScreen.systemHideNotchScreen(this) + "\n"
            );
        }


        Log.e(TAG, "initView: " + System.currentTimeMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " + System.currentTimeMillis());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " + System.currentTimeMillis());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " + System.currentTimeMillis());
    }

    @Override
    public void beforeSetContentView(Bundle savedInstanceState) {
//        Pie.openFullScreenModel(this);
    }


}
