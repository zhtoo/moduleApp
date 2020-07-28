package com.zht.common.NotchScreen;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZhangHaitao on 2018/10/30
 * Android P 系统
 */
@TargetApi(28)
public class Pie {

    private static final String TAG = "Pie";

    /**
     * 是否使用刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInScreen(AppCompatActivity context) {
        View decorView = context.getWindow().getDecorView();
        if (decorView == null) {
            Log.e(TAG, "DecorView is null");
            return false;
        }

        WindowInsets windowInsets = decorView.getRootWindowInsets();
        if (windowInsets == null) {
            Log.e(TAG, "WindowInsets is null");
            return false;
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
            Log.e(TAG, "Version code is less than 29");
            return false;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Log.e(TAG, "this device does not support display cutout");
            return false;
        }

        List<Rect> boundingRects = displayCutout.getBoundingRects();
        if (boundingRects == null || boundingRects.size() == 0) {
            Log.e(TAG, "this device does not show display cutout");
            return false;
        }
        return true;
        //getBoundingRects返回List<Rect>,每一个list表示一个不可显示的区域，即刘海屏，可以遍历这个list中的Rect,
        //即可以获得每一个刘海屏的坐标位置,当然你也可以用类似getSafeInsetBottom的api
//        Log.e(TAG, "Rects:" + displayCutout.getBoundingRects());
//        Log.e(TAG, "Bottom:" + displayCutout.getSafeInsetBottom());
//        Log.e(TAG, "Left:" + displayCutout.getSafeInsetLeft());
//        Log.e(TAG, "Right:" + displayCutout.getSafeInsetRight());
//        Log.e(TAG, "Top:" + displayCutout.getSafeInsetTop());
//
//        for (Rect boundingRect : boundingRects) {
//            int left = boundingRect.left;
//            int top = boundingRect.top;
//            int right = boundingRect.right;
//            int bottom = boundingRect.bottom;
//            Log.e(TAG, "Left:" + left);
//            Log.e(TAG, "Top:" + top);
//            Log.e(TAG, "Right:" + right);
//            Log.e(TAG, "Bottom:" + bottom);
//        }

    }


    public static List<Rect> getNotchScreenSize(AppCompatActivity context) {
        List<Rect> notchScreenSizes = new ArrayList<>();
        View decorView = context.getWindow().getDecorView();
        if (decorView == null) {
            Log.e(TAG, "DecorView is null");
            return notchScreenSizes;
        }

        WindowInsets windowInsets = decorView.getRootWindowInsets();
        if (windowInsets == null) {
            Log.e(TAG, "WindowInsets is null");
            return notchScreenSizes;
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
            Log.e(TAG, "Version code is less than 29");
            return notchScreenSizes;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Log.e(TAG, "this device does not support display cutout");
            return notchScreenSizes;
        }

        List<Rect> boundingRects = displayCutout.getBoundingRects();
        if (boundingRects == null || boundingRects.size() == 0) {
            Log.e(TAG, "this device does not show display cutout");
            return notchScreenSizes;
        }
        notchScreenSizes.addAll(displayCutout.getBoundingRects());
        return notchScreenSizes;
    }



    public static void openFullScreenModel(AppCompatActivity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        activity.getWindow().setAttributes(lp);
        View decorView = activity.getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        systemUiVisibility |= flags;
        activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }


}
