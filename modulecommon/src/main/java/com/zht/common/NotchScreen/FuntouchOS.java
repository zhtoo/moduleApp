package com.zht.common.NotchScreen;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.Method;

/**
 * Created by ZhangHaitao on 2018/10/30
 * VIVO系统
 */
public class FuntouchOS {

    //是否是凹型屏
    public static final int HAS_Feature_SUPPORT = 0x00000020;
    //是否有圆角
    public static final int HAS_FILLET = 0x00000008;

    /**
     * 是否有刘海屏
     *
     * @return
     */
    public static boolean hasNotchScreen() {
        return isFeatureSupport(HAS_Feature_SUPPORT);
    }

    /**
     * 是否有圆角
     *
     * @return
     */
    public static boolean hasFillet() {
        return isFeatureSupport(HAS_FILLET);
    }

    /**
     * 用来判断是否凹型屏，是否有圆角
     * @param mask HAS_Feature_SUPPORT / HAS_FILLET
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


    public static int[] getNotchSize(Context context) {
        int[] mNotchSize = new int[]{0, 0};
        mNotchSize[0] =(int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,
                context.getResources().getDisplayMetrics());
        mNotchSize[1] = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP,27,
                context.getResources().getDisplayMetrics());;
        return mNotchSize;
    }

}
