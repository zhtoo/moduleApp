package com.zht.common.NotchScreen;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by ZhangHaitao on 2018/10/30
 * 华为系统 EMUI Android O适配
 */
public class EMUI {

    /**
     * 是否有刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInScreen(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            hasNotch = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return hasNotch;
        }
    }

    /**
     * 刘海尺寸
     *
     * @param context
     * @return int[0]：width    int[1]：height
     */
    public static int[] getNotchSize(Context context) {
        int[] mNotchSize = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            mNotchSize = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            return mNotchSize;
        }
    }

    /**
     * 「隐藏屏幕刘海」是否开启了
     *
     * @param context
     * @return
     */
    public static boolean systemHideNotchScreen(Context context) {
        boolean isHide;
        String DISPLAY_NOTCH_STATUS = "display_notch_status";
        int mIsNotchSwitchOpen = Settings.Secure
                .getInt(context.getContentResolver(), DISPLAY_NOTCH_STATUS, 0);
        isHide = (mIsNotchSwitchOpen == 1);// 0表示“默认”，1表示“隐藏显示区域”
        return isHide;
    }


}
