package com.zht.common.NotchScreen;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ZhangHaitao on 2018/10/27
 */
public class NotchInScreen {


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

    //是否是凹型屏
    public static final int HAS_Feature_SUPPORT = 0x00000020;
    //是否有圆角
    public static final int HAS_FILLET = 0x00000008;

    /**
     * 反射方法
     *
     * @param mask
     * @return
     */
    public static boolean isFeatureSupport(int mask) {
        boolean hasNotch = false;
        Class<?> FtFeature = null;
        try {
            FtFeature = Class.forName("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            hasNotch = (Boolean) method.invoke(FtFeature, mask);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return hasNotch;
        }

    }


    public static String get(String key) {
        String value = "";
        Class<?> cls = null;
        try {
            cls = Class.forName("android.os.SystemProperties");
            Method hideMethod = cls.getMethod("get", String.class);
            Object object = cls.newInstance();
            value = (String) hideMethod.invoke(object, key);
        } catch (ClassNotFoundException e) {
            Log.e("error", "get error() ", e);
        } catch (NoSuchMethodException e) {
            Log.e("error", "get error() ", e);
        } catch (InstantiationException e) {
            Log.e("error", "get error() ", e);
        } catch (IllegalAccessException e) {
            Log.e("error", "get error() ", e);
        } catch (IllegalArgumentException e) {
            Log.e("error", "get error() ", e);
        } catch (InvocationTargetException e) {
            Log.e("error", "get error() ", e);
        }
        return value;
    }




}
