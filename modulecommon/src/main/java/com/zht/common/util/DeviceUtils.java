package com.zht.common.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by ZhangHaitao on 2020/7/29
 */
public class DeviceUtils {

    /**
     * 判断当前设备是手机还是平板，
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    


}
