package com.zht.common.NotchScreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;


/**
 * Created by ZhangHaitao on 2018/10/30
 * Android P 系统
 */
@TargetApi(28)
public class Pie {

    /**
     * 是否有刘海屏
     *
     * @param context
     * @return
     */
    public static boolean hasNotchInScreen(Activity context) {
        View decorView = context.getWindow().getDecorView();
        if (decorView != null) {
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                    //getBoundingRects返回List<Rect>,每一个list表示一个不可显示的区域，即刘海屏，可以遍历这个list中的Rect,
                    //即可以获得每一个刘海屏的坐标位置,当然你也可以用类似getSafeInsetBottom的api
                    Log.e("test", "Rects:" + displayCutout.getBoundingRects());
                    Log.e("test", "Bottom:" + displayCutout.getSafeInsetBottom());
                    Log.e("test", "Left:" + displayCutout.getSafeInsetLeft());
                    Log.e("test", "Right:" + displayCutout.getSafeInsetRight());
                    Log.e("test", "Top:" + displayCutout.getSafeInsetTop());
                }
            }
        }
        return false;
    }

}
