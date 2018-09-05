package com.zht.modulelib.StatusBar;

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
 * @describe:状态栏工具类 1、设置状态栏的颜色
 * 2、设置透明状态栏（布局占用状态栏的位置）
 */

public class StatusBar {





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
     * 设置全透明状态栏   --- 状态栏不可见
     * 调用后再调用设设置状态栏字体 ，状态栏的控件会重新显示
     * @param activity
     */
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarLollipop.translucentStatusBar(activity, true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarKitKat.translucentStatusBar(activity);
        } else {
            Log.e("StatusBar", "全透明状态栏颜色设置无效");
        }
    }


    /**
     * 设置半透明状态栏
     * 调用后再调用设设置状态栏字体 ，状态栏的控件不会重新显示
     * @param activity
     */
    public static void setTranslucentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarLollipop.translucentStatusBar(activity, false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarKitKat.translucentStatusBar(activity);
        } else {
            Log.e("StatusBar", "半透明状态栏颜色设置无效");
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
        } else if (MIUISetStatusBarLightMode(activity, darkmode)
                || FlymeSetStatusBarLightMode(activity, darkmode)) {
            //小米手机设置状态栏字体颜色 黑体/白色
            //魅族手机设置状态栏字体颜色 黑体/白色
        } else {
            Log.e("StatusBar", "状态栏字体颜色设置无效");
        }
    }


    /**
     * MIUI的沉浸支持透明白色字体和透明黑色字体,MIUI6.0以上
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
        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
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
        return false;
    }
}
