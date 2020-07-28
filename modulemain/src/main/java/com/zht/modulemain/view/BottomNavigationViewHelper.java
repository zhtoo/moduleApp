package com.zht.modulemain.view;


import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;


/**
 * Created by ZhangHaitao on 2018/9/3.
 */
public class BottomNavigationViewHelper {

    /**
     * 禁用切换模式
     *
     * @param view BottomNavigationView
     */
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView child = (BottomNavigationItemView) menuView.getChildAt(i);
                Class<?> clazz = child.getClass();
//                Method setShifting = clazz.getDeclaredMethod("setShifting", boolean.class);
//                setShifting.setAccessible(true);//设置允许访问
//                setShifting.invoke(clazz,false);

                Field isShifting = clazz.getDeclaredField("isShifting");
                isShifting.setAccessible(true);
                isShifting.setBoolean(clazz,false);
            }
        } catch (Exception e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        }
    }

}
