package com.zht.common.StatusBar;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 作者：zhanghaitao on 2017/12/19 16:06
 * 邮箱：820159571@qq.com
 *
 * @describe:状态栏工具类
 */
public class StatusBar {


    private static int STATUS_BAR = 0;//状态栏(字体默认)白色
    private static int STATUS_BAR_LIGHT = 1;//状态栏(字体高亮)黑色
    private static int FULLSCREEN = 2;//全屏显示(字体默认)白色
    private static int FULLSCREEN_LIGHT = 3;//全屏显示(字体高亮)黑色
    private static int NO_STATUS_BAR = 4;//去掉状态栏


    /**
     * 1、是否显示状态栏
     * 2、是否改变字体
     * 3、是否全屏
     * 4、设置字体颜色
     */
    public static void setStatusBar(Activity activity, int statusBarColor, int type) {
        if (type == NO_STATUS_BAR) {
            setStatusBarVisibility(activity, false);
            return;
        }

    }

    /**
     * 显示/隐藏状态栏
     *
     * @param activity
     * @param isVisibility
     */
    public static void setStatusBarVisibility(Activity activity, boolean isVisibility) {
        if (isVisibility) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//5.0+
            //在4.4中状态栏是假的，因此需要自己手动移除
            StatusBarKitKat.setStatusBarVisibility(activity, isVisibility);
        }

    }

    /**
     * 设置全屏
     *
     * @param activity
     * @param isFullScreen
     */
    public static void setFullScreen(Activity activity, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0+
            StatusBarLollipop.setFullScreen(activity, isFullScreen);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            StatusBarKitKat.setFullScreen(activity, isFullScreen);
        } else {
            Log.e("StatusBar", "设置设置全屏无效");
        }
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param activity
     * @param statusColor
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0+
            StatusBarLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
            StatusBarKitKat.setStatusBarColor(activity, statusColor);
        } else {
            Log.e("StatusBar", "状态栏颜色设置无效");
        }
    }

    /**
     * 设置状态栏字体颜色
     * 需要区别的是：在Android6.0以下，MIUI6.0+和Flyme4.0+ 支持修改状态栏字体颜色。
     *
     * @param activity
     * @param darkmode
     */
    public static void setStatusBarLight(Activity activity, boolean darkmode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android系统设6.0+ 态栏字体颜色 黑体/白色
            int visibility = darkmode ?
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR ://设置状态栏黑色字体
                    View.SYSTEM_UI_FLAG_VISIBLE;//恢复状态栏白色字体
            activity.getWindow().getDecorView()
                    .setSystemUiVisibility(visibility);
        } else if (OSUtil.isMiui()) {
            //小米手机设置状态栏字体颜色 黑体/白色
            MIUISetStatusBarLightMode(activity, darkmode);
        } else if (OSUtil.isFlyme()) {
            //魅族手机设置状态栏字体颜色 黑体/白色
            FlymeSetStatusBarLightMode(activity, darkmode);
        } else {
            Log.e("StatusBar", "状态栏字体颜色设置无效");
        }
    }

    /**
     * MIUI的修改状态栏字体颜色,MIUI6.0以上
     * https://dev.mi.com/console/doc/detail?pId=1159
     */
    static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            Class<? extends Window> clazz = activity.getWindow().getClass();
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("StatusBar", "小米手机状态栏字体颜色设置无效");
        return false;
    }

    /**
     * Flyme的修改状态栏字体颜色，Flyme4.0以上
     */
    static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("StatusBar", "魅族手机状态栏字体颜色设置无效");
        return false;
    }

}
