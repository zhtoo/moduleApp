package com.zht.common.NotchScreen;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by ZhangHaitao on 2018/10/30
 * OPPO系统
 */
public class ColorOS {
    /**
     * 是否有刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInScreen(Context context) {
        //回 true为凹形屏
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }


    /**
     * 具体返回格式是 [378,0:702,80]   什么意思？？？
     * 凹型屏的刘海部分看作是一个矩形。[378,0:702,80] 就代表它的左上角和右下角的坐标。值得注意的是
     * [378,0:702,80]对应的是[横坐标X,纵坐标Y:横坐标X1,纵坐标Y] ===> [X,Y:X1,Y]
     */
    public static int[] getNotchSize(Context context) {
        int[] mNotchSize = new int[]{0, 0};
        String mProperty = "";
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", String.class);
            mProperty = (String) get.invoke(SystemProperties, "ro.oppo.screen.heteromorphism");

            Log.e("OPPO系统", "mProperty" + mProperty);
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


}
