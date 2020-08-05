package com.zht.common.util;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by ZhangHaitao on 2018/10/20
 * 获取屏幕尺寸相关数据
 * <p>
 * 知识点：
 * 1、activity.getResources()和Resources.getSystem()的区别，一般情况下两个方法获取的值是相同的。
 * 前者跟随应用变化，后者跟随系统变化。activity中的context被应用修改过后，前者获取的值可能会被干扰，后者则不会
 */
public class SystemScreenUtils {


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取底部导航栏的高度
     */
    public static int getNavigationBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }


    /**
     * 获取屏幕密度比值
     */
    public static float getDensity() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.density;
    }


    /**
     * 获取屏幕密度:每英寸的像素
     */
    public static int getDensityDpi() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.densityDpi;
    }


    /**
     * 获取文字缩放比,用于计算文字大小。xxxsp 对应的px为：  xxx * scaledDensity
     */
    public static float getSaledDensity() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.scaledDensity;
    }

    /**
     * 水平（x轴）方向的真实密度
     * @return
     */
    public static float getXDpi() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.xdpi;
    }

    /**
     * 垂直（y轴）方向的真是密度
     * @return
     */
    public static float getYDpi() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.ydpi;
    }

    public static float getScreenInch() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float xInch = displayMetrics.widthPixels / displayMetrics.xdpi;
        float yInch = displayMetrics.heightPixels / displayMetrics.ydpi;
        return (float) formatDouble(
                Math.sqrt(xInch * xInch + yInch * yInch),
                1);
    }

    public static float getScreenXInch() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (float) formatDouble((displayMetrics.widthPixels / displayMetrics.xdpi), 2);
    }

    public static float getScreenYInch() {
        Resources resources = Resources.getSystem();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (float) formatDouble((displayMetrics.heightPixels / displayMetrics.ydpi), 2);
    }




    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    public static int getAvailableScreenHeight(AppCompatActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取虚拟按键的高度，不论虚拟按键是否显示都会返回其固定高度
     */
    public static int getVirtualBarHeight(AppCompatActivity activity) {
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 标题栏高度，如果隐藏了标题栏则返回零
     */
    public static int getTitleHeight(AppCompatActivity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获取屏幕真实尺寸单位PX
     *
     * @param activity
     * @return
     */
    public static Point getDisplayInfomation(AppCompatActivity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        Log.e("TAG", "the screen size is " + point.toString());
        //获取正确尺寸 （此方法要求最低api为17，
        // 即安卓4.2，4.2之前获取请参看以下获取屏幕尺寸的方法）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealSize(point);
            Log.e("TAG", "the screen real size is " + point.toString());
        }
        return point;
    }

    /**
     * 获取屏幕宽高尺寸，单位英寸
     *
     * @param context
     * @return
     */
    public static PointF getScreenXYInch(AppCompatActivity context) {
        PointF point = new PointF();
        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (Build.VERSION.SDK_INT < 17
                    && Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }
            point.x = (float) formatDouble((realWidth / metrics.xdpi), 1);
            point.y = (float) formatDouble((realHeight / metrics.ydpi), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return point;
    }

    /**
     * 获取屏幕尺寸，单位英寸
     *
     * @param context
     * @return
     */
    public static double getScreenInch(AppCompatActivity context) {
        PointF screenInch = getScreenXYInch(context);
        return formatDouble(
                Math.sqrt(screenInch.x * screenInch.x + screenInch.y * screenInch.y),
                1);
    }

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d, int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
