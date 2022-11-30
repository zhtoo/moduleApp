package com.zht.moduletool.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.NotchScreen.NotchScreen;
import com.zht.common.NotchScreen.Pie;
import com.zht.common.StatusBar.StatusBar;
import com.zht.common.base.BaseActivity;
import com.zht.common.util.ScreenUtils;
import com.zht.common.util.SystemScreenUtils;
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
        StatusBar.setFullScreen(this, true);
        StatusBar.setStatusBarColor(this, Color.TRANSPARENT);

        setViewHeight(findViewById(R.id.status_bar_container), SystemScreenUtils.getStatusBarHeight());
        setViewHeight(findViewById(R.id.navigation_bar_container), SystemScreenUtils.getNavigationBarHeight());
    }

    public void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 屏幕适配方案
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            final Resources res = newBase.getResources();
            final Configuration config = res.getConfiguration();
            //修改configuration的fontScale属性
            config.fontScale = 1;
            final Context newContext;
            final float targetDensity = res.getDisplayMetrics().widthPixels / 375F;
            final float targetScaledDensity = targetDensity;
            final int targetDensityDpi = (int) (160 * targetDensity);
            config.densityDpi = targetDensityDpi;
            newContext = newBase.createConfigurationContext(config);
            /**
             * DisplayMetrics:
             * widthPixels：屏幕宽度
             * heightPixels：屏幕高度
             * densityDpi：屏幕密度，每英寸的像素数。计算公式：
             *             Math.sqart（widthPixels*widthPixels+heightPixels*heightPixels）(开根号）/ 屏幕大小（英寸，屏幕对角线长度的英寸值）
             * density：密度比值，和densityDpi相关，
             *          公式：density = densityDpi/160  （160密度是谷歌定的一个密度标准），
             *          不同手机dp换算px就是通过该值。
             * scaledDensity:同density，用于文字缩放的计算，也就是sp
             * xDpi：水平（x轴）方向的真实密度
             * yDpi：垂直（y轴）方向的真是密度
             *
             * 屏幕尺寸计算公式： Math.sqart（widthPixels*widthPixels+heightPixels*heightPixels）(开根号）/ densityDpi
             *
             *
             */
            DisplayMetrics displayMetrics = newContext.getResources().getDisplayMetrics();
            displayMetrics.density = targetDensity;
            displayMetrics.scaledDensity = targetScaledDensity;
            displayMetrics.densityDpi = targetDensityDpi;
            // displayMetrics.xdpi = targetDensityDpi;
            super.attachBaseContext(newContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    /**
     * 屏幕适配方案（兼容17以下）
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //获取Configuration对象
            Configuration configuration = res.getConfiguration();
            //修改configuration的fontScale属性
            configuration.fontScale = 1;
            final float targetDensity =
                    res.getDisplayMetrics().widthPixels / 375F;
            final float targetScaledDensity = targetDensity;
            final int targetDensityDpi = (int) (160 * targetDensity);
//            configuration.densityDpi = targetDensityDpi;
            DisplayMetrics displayMetrics = res.getDisplayMetrics();
            displayMetrics.density = targetDensity;
            displayMetrics.scaledDensity = targetScaledDensity;
            displayMetrics.densityDpi = targetDensityDpi;
            /**
             * updateConfiguration方法在Api25中废弃，
             * 原文是建议改用createConfigurationContext
             * 而createConfigurationContext这个方法没有具体的说明调用情况，
             * 所以本方案是在attachBaseContext中添加
             */
            res.updateConfiguration(res.getConfiguration(), displayMetrics);
        }
        return res;
    }


    @SuppressLint("DefaultLocale")
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mText = findViewById(R.id.screen_text);
        mText.setMovementMethod(ScrollingMovementMethod.getInstance());


        PointF screenXYInch = ScreenUtils.getScreenXYInch(this);
        Point screenDisplay = ScreenUtils.getDisplayInformation(this);

        String mScreenMessage =
                "ScreenUtils(activity.getResources()) ->" + "\n" +
                        String.format("屏幕分辨率：%d * %d px", ScreenUtils.getTotalScreenWidth(this), ScreenUtils.getTotalScreenHeight(this)) + "\n" +
                        String.format("屏幕可用分辨率：%d * %d px", ScreenUtils.getAvailableScreenWidth(this), ScreenUtils.getAvailableScreenHeight(this)) + "\n" +
                        String.format("屏幕尺寸：%.1f * %.1f = %.1f 英寸", screenXYInch.x, screenXYInch.y, ScreenUtils.getScreenInch(this)) + "\n" +
                        String.format("Display屏幕分辨率：%d * %d px", screenDisplay.x, screenDisplay.y) + "\n" +
                        String.format("状态栏高度：%d px", ScreenUtils.getStatusBarHeight(this)) + "\n" +
                        String.format("导航栏高度：%d px", ScreenUtils.getNavigationBarHeight(this)) + "\n" +
                        String.format("屏幕密度：%d  dp\n", ScreenUtils.getDensityDpi(this)) +
                        //     String.format("密度：%.3f * %.3f dp\n", SystemScreenUtils.getXDpi(), SystemScreenUtils.getYDpi()) +
                        String.format("密度比值：%f \n", ScreenUtils.getDensity(this)) +
                        String.format("文字缩放比：%f \n", ScreenUtils.getScaledDensity(this)) +
                        "\n";

        StringBuffer sb = new StringBuffer();
        sb.append(mScreenMessage);


        sb.append("SystemScreenUtils(Resources.getSystem()) ->\n");
        sb.append(String.format("屏幕分辨率：%d * %d px\n",
                SystemScreenUtils.getTotalScreenWidth(mText.getContext()), SystemScreenUtils.getTotalScreenHeight(mText.getContext())));
        sb.append(String.format("屏幕可用分辨率：%d * %d px\n",
                SystemScreenUtils.getScreenWidth(), SystemScreenUtils.getScreenHeight()));
        sb.append(String.format("屏幕可用尺寸：%.1f * %.1f = %.1f 英寸 \n",
                SystemScreenUtils.getScreenXInch(),
                SystemScreenUtils.getScreenYInch(),
                SystemScreenUtils.getScreenInch()));
        sb.append(String.format("状态栏高度：%d px \n",
                SystemScreenUtils.getStatusBarHeight()));
        sb.append(String.format("底部导航栏高度：%d px \n",
                SystemScreenUtils.getNavigationBarHeight()));


        sb.append(String.format("屏幕密度：%d  dp\n",
                SystemScreenUtils.getDensityDpi()));
        sb.append(String.format("真实密度：%.3f * %.3f dp\n",
                SystemScreenUtils.getXDpi(), SystemScreenUtils.getYDpi()));

        sb.append(String.format("密度比值：%f \n",
                SystemScreenUtils.getDensity()));
        sb.append(String.format("文字缩放比：%f \n",
                SystemScreenUtils.getScaledDensity()));


        sb.append("\n");

        sb.append("getResources() ->\n");
        sb.append(String.format("1dp = %f px \n", TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                getResources().getDisplayMetrics())));

        sb.append(String.format("1sp = %f px \n", TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1,
                getResources().getDisplayMetrics())));

        sb.append(String.format("densityDpi = %d dp \n",
                getResources().getDisplayMetrics().densityDpi));
        sb.append(String.format("density = %f px \n",
                getResources().getDisplayMetrics().density));
        sb.append(String.format("scaledDensity = %f  \n",
                getResources().getDisplayMetrics().scaledDensity));
        sb.append(String.format("fontScale = %f   \n",
                getResources().getConfiguration().fontScale));


        Point display = ScreenUtils.getDisplayInformation(this);
        double screenInch = ScreenUtils.getScreenInch(this);

        double densityDpi = Math.sqrt(display.x * display.x + display.y * display.y) / screenInch;

        sb.append(String.format("计算 densityDpi = %f px \n", densityDpi));
        sb.append(String.format("计算 density = %f px \n", densityDpi / 160));

        sb.append("\n");
        mText.setText(sb.toString());

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

                        View notchArea = findViewById(R.id.notch_area);
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) notchArea.getLayoutParams();
                        layoutParams.width = rect.width();
                        layoutParams.height = rect.height();
                        layoutParams.leftMargin = rect.left;
                        layoutParams.topMargin = rect.top;
                        notchArea.setLayoutParams(layoutParams);

                    }
                    s += "是否是Android Pie:true" + "\n" +
                            "系统:" + NotchScreen.getOS() + "\n" +
                            "是否是刘海屏:" + Pie.hasNotchInScreen(ScreenActivity.this) + "\n" +
                            "获取凹型屏的宽度:" + width + "\n" +
                            "获取凹型屏的高度:" + height + "\n" +
                            "「隐藏屏幕刘海」是否开启了:" + NotchScreen.systemHideNotchScreen(ScreenActivity.this) + "\n"
                    ;


                    mText.setText(s +
                            "厂商方案 ->" + "\n" +
                            "是否是刘海屏:" + NotchScreen.hasNotchScreen(ScreenActivity.this) + "\n" +
                            "获取凹型屏的宽度:" + NotchScreen.getNotchSize(ScreenActivity.this)[0] + "\n" +
                            "获取凹型屏的高度:" + NotchScreen.getNotchSize(ScreenActivity.this)[1] + "\n" +
                            "「隐藏屏幕刘海」是否开启了:" + NotchScreen.systemHideNotchScreen(ScreenActivity.this) + "\n"
                    );
//
//                    mText.setText(s);

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
        //Pie.openFullScreenModel(this);
    }


}
