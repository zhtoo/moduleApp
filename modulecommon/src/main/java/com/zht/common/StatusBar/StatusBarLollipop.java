package com.zht.common.StatusBar;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

/**
 * @Date 2018/5/23 16:07
 * @Author ZhangHaitao
 * @Version
 * @Description 基于Android5.0版本的状态栏工具类
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarLollipop {

    /**
     * 设置全屏
     *
     * @param activity
     * @param isFullScreen
     */
    public static void setFullScreen(AppCompatActivity activity, boolean isFullScreen) {
        Window window = activity.getWindow();
        if (isFullScreen) {//全屏
            //设置状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {//非全屏
            //设置状态栏可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param activity
     * @param statusColor
     */
    public static void setStatusBarColor(AppCompatActivity activity, int statusColor) {
        Window window = activity.getWindow();
        //显示状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }


//    static void setStatusBarColor(Activity activity, int statusColor) {
//        Window window = activity.getWindow();
//        //取消状态栏透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        window.setStatusBarColor(statusColor);
//        //设置系统状态栏处于可见状态
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        //让view不根据系统窗口来调整自己的布局
//        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            mChildView.setFitsSystemWindows(false);
//            ViewCompat.requestApplyInsets(mChildView);
//        }
//    }

//    /**
//     * @param activity
//     * @param hideStatusBarBackground
//     */
//    static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
//        Window window = activity.getWindow();
//        //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        if (hideStatusBarBackground) {
//            //如果为全透明模式，取消设置Window半透明的Flag
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //设置状态栏为透明
//            window.setStatusBarColor(Color.TRANSPARENT);
//            //设置window的状态栏不可见
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        } else {
//            //如果为半透明模式，添加设置Window半透明的Flag
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //设置系统状态栏处于可见状态
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        }
//        //view不根据系统窗口来调整自己的布局
//        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            mChildView.setFitsSystemWindows(false);
//            ViewCompat.requestApplyInsets(mChildView);
//        }
//    }


}
