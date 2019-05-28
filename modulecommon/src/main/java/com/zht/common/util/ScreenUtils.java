package com.zht.common.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by ZhangHaitao on 2018/10/20
 * 获取屏幕尺寸相关数据
 */
public class ScreenUtils {
    /**
     * 返回包括虚拟键在内的总的屏幕高度
     * 即使虚拟按键显示着，也会加上虚拟按键的高度
     */
    public static int getTotalScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 返回屏幕的宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    public static int getAvailableScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 获取固定状态栏高度
     */
    public static int getStatusBarHeight1(Activity activity) {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取虚拟按键的高度
     * 会根据当前是否有显示虚拟按键来返回相应的值
     * 即如果隐藏了虚拟按键，则返回零
     */
    public static int getVirtualBarHeightIfRoom(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int usableHeight = displayMetrics.heightPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        }
        int realHeight = displayMetrics.heightPixels;
        return realHeight - usableHeight;
    }

    /**
     * 获取虚拟按键的高度，不论虚拟按键是否显示都会返回其固定高度
     */
    public static int getVirtualBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 标题栏高度，如果隐藏了标题栏则返回零
     */
    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获取屏幕真实尺寸单位PX
     * @param activity
     * @return
     */
    public static Point getDisplayInfomation(Activity activity) {
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
    public static PointF getScreenXYInch(Activity context) {
        PointF point = new PointF();
        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }
            point.x = (float) formatDouble((realWidth / metrics.xdpi),1);
            point.y = (float) formatDouble((realHeight / metrics.ydpi),1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return point;
    }

    /**
     * 获取屏幕尺寸，单位英寸
     * @param context
     * @return
     */
    public static double getScreenInch(Activity context) {
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
