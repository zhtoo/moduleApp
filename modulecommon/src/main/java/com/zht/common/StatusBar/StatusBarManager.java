package com.zht.common.StatusBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ZhangHaitao on 2018/11/8.
 */

public class StatusBarManager {

    //是否展示状态栏
    private static boolean dontShowStatusBar;
    //是否用黑色字体
    private static boolean isDarkMode;
    //是否在状态栏中展示内容
    private static boolean showInStatusBar;

    //状态栏颜色
    private static int statusColor;

    private static AppCompatActivity mActivity;


    public static boolean isDontShowStatusBar() {
        return dontShowStatusBar;
    }

    public static void setDontShowStatusBar(boolean dontShowStatusBar) {
        StatusBarManager.dontShowStatusBar = dontShowStatusBar;
    }

    public static boolean isDarkMode() {
        return isDarkMode;
    }

    public static void setIsDarkMode(boolean isDarkMode) {
        StatusBarManager.isDarkMode = isDarkMode;
    }

    public static boolean isShowInStatusBar() {
        return showInStatusBar;
    }

    public static void setShowInStatusBar(boolean showInStatusBar) {
        StatusBarManager.showInStatusBar = showInStatusBar;
    }

    public static int getStatusColor() {
        return statusColor;
    }

    public static void setStatusColor(int statusColor) {
        StatusBarManager.statusColor = statusColor;
    }

    public static AppCompatActivity getActivity() {
        return mActivity;
    }

    public static void setActivity(AppCompatActivity mActivity) {
        StatusBarManager.mActivity = mActivity;
    }

    public void build() {


    }
}
