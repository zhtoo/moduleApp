package com.zht.modulemain.view;


import android.annotation.SuppressLint;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zht.modulemain.R;


/**
 * Created by ZhangHaitao on 2018/9/3.
 */
public class BottomNavigationViewHelper {

    /**
     * 在 app:labelVisibilityMode="labeled" 时，删除BottomNavigationView切换tab动画
     *
     * @param view BottomNavigationView
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            int itemTextAppearanceActive = menuView.getItemTextAppearanceActive();
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView child = (BottomNavigationItemView) menuView.getChildAt(i);
                TextView smallLabel = child.findViewById(R.id.smallLabel);
                TextView largeLabel = child.findViewById(R.id.largeLabel);
                if (smallLabel != null && largeLabel != null) {
                    float min = Math.min(smallLabel.getTextSize(), largeLabel.getTextSize());
                    smallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, min);
                    largeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, min);
                    child.setTextAppearanceActive(itemTextAppearanceActive);
                    child.setChecked(i == menuView.getSelectedItemId());
                }
            }
        } catch (Exception e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        }
    }

}
