package com.zht.common.base;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.kotlin.datastore.DataStoreUtil;


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

        //初始化DataStore
        DataStoreUtil.INSTANCE.init(this);

    }
    /**获取系统上下文 */
    public static Context getAppContext() {
        return mAppContext;
    }
}
