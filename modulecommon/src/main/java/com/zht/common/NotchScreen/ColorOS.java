package com.zht.common.NotchScreen;

import android.content.Context;
import android.text.TextUtils;
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
    public static boolean hasNotchScreen(Context context) {
        //回 true为凹形屏
        return context.getPackageManager()
                .hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 具体返回格式是 [378,0:702,80]   什么意思？？？
     * 凹型屏的刘海部分看作是一个矩形。[378,0:702,80] 就代表它的左上角和右下角的坐标。值得注意的是
     * [378,0:702,80]对应的是[横坐标X,纵坐标Y:横坐标X1,纵坐标Y1] ===> X,Y:X1,Y1
     */
    public static int[] getNotchSize(Context context) {
        //屏幕顶部凹形区域不能显示内容，宽度为324px,  高度为80px。
        int[] mNotchSize = new int[]{324, 80};
        String mProperty = "";
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", String.class);
            mProperty = (String) get.invoke(SystemProperties, "ro.oppo.screen.heteromorphism");
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        }
        if (!TextUtils.isEmpty(mProperty)) {
            try {
                int x, y, x1, y1 = 0;
                String[] split = mProperty.replaceAll(":", ",").split(",");
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
                x1 = Integer.parseInt(split[2]);
                y1 = Integer.parseInt(split[3]);
                if (x1 - x > 0) {
                    mNotchSize[0] = x1 - x;
                }
                if (y1 - y > 0) {
                    mNotchSize[1] = y1 - y;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return mNotchSize;
    }

}
