package com.zht.moduletool.sample;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.NotchScreen.NotchScreen;
import com.zht.common.NotchScreen.Pie;
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
    }


    /**
     * 屏幕适配方案
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
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
        if (Build.VERSION.SDK_INT < 17) {
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mText = findViewById(R.id.screen_text);
        mText.setMovementMethod(ScrollingMovementMethod.getInstance());

        String mScreenMessage =
                "总屏幕高度(包括虚拟键):" + ScreenUtils.getTotalScreenHeight(this) + "\n" +
                        "总屏幕宽度:" + ScreenUtils.getScreenWidth(this) + "\n" +
                        "屏幕可用高度:" + ScreenUtils.getAvailableScreenHeight(this) + "\n" +
                        "状态栏高度:" + ScreenUtils.getStatusBarHeight(this) + "\n" +
                        "状态栏高度（R）:" + ScreenUtils.getStatusBarHeight1() + "\n" +
                        "虚拟按键的高度(未显示为0):" + ScreenUtils.getVirtualBarHeightIfRoom(this) + "\n" +
                        "虚拟按键的高度:" + ScreenUtils.getVirtualBarHeight(this) + "\n" +
                        "标题栏高度(未显示为0):" + ScreenUtils.getTitleHeight(this) + "\n" +
                        "获取屏幕宽高尺寸:" + ScreenUtils.getScreenXYInch(this).toString() + "\n" +
                        "获取屏幕尺寸:" + ScreenUtils.getScreenInch(this) + "\n" +
                        "获取屏幕真实尺寸单位PX:" + ScreenUtils.getDisplayInfomation(this).toString() + "\n"
                        + "\n";

        StringBuffer sb = new StringBuffer();
        sb.append(mScreenMessage);

        sb.append(String.format("1dp = %f px \n", TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                getResources().getDisplayMetrics())));

        sb.append(String.format("1sp = %f px \n", TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1,
                getResources().getDisplayMetrics())));

        sb.append(String.format("densityDpi = %d dp \n",
                getResources().getDisplayMetrics().densityDpi));
        sb.append(String.format("density = %f px \n",
                getResources().getDisplayMetrics().density));
        sb.append(String.format("scaledDensity = %f px \n",
                getResources().getDisplayMetrics().scaledDensity));
        sb.append(String.format("fontScale = %f   \n",
                getResources().getConfiguration().fontScale));


        Point display = ScreenUtils.getDisplayInfomation(this);
        double screenInch = ScreenUtils.getScreenInch(this);

        double densityDpi = Math.sqrt(display.x * display.x + display.y * display.y) / screenInch;

        sb.append(String.format("计算 densityDpi = %f px \n", densityDpi));
        sb.append(String.format("计算 density = %f px \n", densityDpi/160));


        sb.append("\n");
        sb.append("Resources.getSystem()\n");
        sb.append(String.format("状态栏高度：%d px \n",
                SystemScreenUtils.getStatusBarHeight()));
        sb.append(String.format("底部导航栏高度：%d px \n",
                SystemScreenUtils.getNavigationBarHeight()));

        sb.append(String.format("屏幕密度，每英寸的像素数：%d  dp\n",
                SystemScreenUtils.getDensityDpi()));
        sb.append(String.format("水平（x轴）方向的真实密度：%f  dp\n",
                SystemScreenUtils.getXDpi()));
        sb.append(String.format("垂直（y轴）方向的真是密度：%f  dp\n",
                SystemScreenUtils.getYDpi()));
        sb.append(String.format("密度比值：%f   \n",
                SystemScreenUtils.getDensity()));
        sb.append(String.format("文字缩放比：%f  (1sp = %f px) \n",
                SystemScreenUtils.getSaledDensity(), SystemScreenUtils.getSaledDensity()));

        sb.append(String.format("屏幕尺寸：%f英寸 = √￣[%f^2(横向英寸的平方) + %f^2(纵向英寸的平方)]\n",
                SystemScreenUtils.getScreenInch(),
                SystemScreenUtils.getScreenXInch(),
                SystemScreenUtils.getScreenYInch()));

        sb.append("\n");


        sb.append("activity.getResources()\n");

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
                    }
                    s += "是否是Android Pie:true" + "\n" +
                            "系统:" + NotchScreen.getOS() + "\n" +
                            "是否是刘海屏:" + Pie.hasNotchInScreen(ScreenActivity.this) + "\n" +
                            "获取凹型屏的宽度:" + width + "\n" +
                            "获取凹型屏的高度:" + height + "\n" +
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
