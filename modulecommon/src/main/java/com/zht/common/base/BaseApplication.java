package com.zht.common.base;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;


public class BaseApplication extends MultiDexApplication {

    private boolean isDebug = true;

    private static Context mAppContext  ;

    @Override
    public void onCreate() {
        super.onCreate();
        if(isDebug){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        mAppContext = this;
    }
    /**获取系统上下文 */
    public static Context getAppContext() {
        return mAppContext;
    }
}
