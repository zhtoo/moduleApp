package com.zht.common.NotchScreen;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Method;

/**
 * Created by ZhangHaitao on 2018/10/27
 * 小米系统 MIUI Android O 适配
 *  https://dev.mi.com/console/doc/
 */
public class MIUI {

    /**
     * 是否是刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchScreen(Context context) {
        boolean hasNotch = false;
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");

            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
//            Method repay2 = SystemProperties.getMethod("getInt", String.class,int.class);//得到方法对象,有参的方法需要指定参数类型
//            repay2.invoke(SystemProperties,"ro.miui.notch",0);//执行方法，有参传参
            Object[] params = new Object[2];
            params[0] = new String("ro.miui.notch");
            params[1] = new Integer(0);
            int notch = (Integer) getInt.invoke(SystemProperties, params);
            hasNotch = (notch == 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return hasNotch;
        }
    }

    public static int[] getNotchSize(Context context) {
        int[] mNotchSize = new int[]{0, 0};
        mNotchSize[0] = getNotchScreenWidth(context);
        mNotchSize[1] = getNotchScreenHeight(context);
        return mNotchSize;
    }

    /**
     * 获取凹型屏的高度
     *
     * @param context
     * @return
     */
    public static int getNotchScreenHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取凹型屏的宽度
     *
     * @param context
     * @return
     */
    public static int getNotchScreenWidth(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 「隐藏屏幕刘海」是否开启了
     *
     * @param context
     * @return
     */
    public static boolean systemHideNotchScreen(Context context) {
        boolean isHide = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            int force_black = Settings.Global.getInt(context.getContentResolver(), "force_black", 0);
            isHide = (force_black == 1);
        }
        return isHide;
    }

}
