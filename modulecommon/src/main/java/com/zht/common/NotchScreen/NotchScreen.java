package com.zht.common.NotchScreen;

import android.content.Context;

/**
 * Created by ZhangHaitao on 2018/10/27
 * VIVO
 */
public class NotchScreen {

    public static String getOS( ) {
       return RomUtils.getRomInfo().toString();
    }

    public static boolean hasNotchScreen(Context context) {
        if (RomUtils.isHuawei()) {
            return EMUI.hasNotchScreen(context);
        } else if (RomUtils.isOppo()) {
            return ColorOS.hasNotchScreen(context);
        } else if (RomUtils.isVivo()) {
            return FuntouchOS.hasNotchScreen();
        } else if (RomUtils.isXiaomi()) {
            return MIUI.hasNotchScreen(context);
        }
        return false;
    }

    public static int[] getNotchSize(Context context) {
        int[] mNotchSize = new int[]{0, 0};
        if (RomUtils.isHuawei()) {
            return EMUI.getNotchSize(context);
        } else if (RomUtils.isOppo()) {
            return ColorOS.getNotchSize(context);
        } else if (RomUtils.isVivo()) {
            return FuntouchOS.getNotchSize(context);
        } else if (RomUtils.isXiaomi()) {
            return MIUI.getNotchSize(context);
        }
        return mNotchSize;
    }

    public static boolean systemHideNotchScreen(Context context) {
        if (RomUtils.isHuawei()) {
            return EMUI.systemHideNotchScreen(context);
        } else if (RomUtils.isOppo()) {
            return false;
        } else if (RomUtils.isVivo()) {
            return false;
        } else if (RomUtils.isXiaomi()) {
            return MIUI.systemHideNotchScreen(context);
        }
        return false;
    }

}
