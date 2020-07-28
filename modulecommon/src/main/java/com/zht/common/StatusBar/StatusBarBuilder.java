package com.zht.common.StatusBar;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by ZhangHaitao on 2018/11/8.
 */

public class StatusBarBuilder {


    private static StatusBarManager mStatusBarManager;

    public static StatusBarManager with(AppCompatActivity activity) {
        if (mStatusBarManager == null) {
            mStatusBarManager = new StatusBarManager();
        }
        mStatusBarManager.setActivity(activity);
        return mStatusBarManager;
    }

    private static StatusBarManager dontShowStatusBar(boolean noShowStatusBar) {
        mStatusBarManager.setDontShowStatusBar(noShowStatusBar);
        return mStatusBarManager;
    }

    private static StatusBarManager isDarkMode(boolean darkMode) {
        mStatusBarManager.setIsDarkMode(darkMode);
        return mStatusBarManager;
    }

    private static StatusBarManager statusColor(@ColorInt int color) {
        mStatusBarManager.setStatusColor(color);
        return mStatusBarManager;
    }

    private static StatusBarManager showInStatusBar(boolean showInStatus) {
        mStatusBarManager.setShowInStatusBar(showInStatus);
        return mStatusBarManager;
    }


    //===统一初始化==============================================================

    private static void build() {

    }

}
