package com.zht.moduleview.activity.system;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by ZhangHaitao on 2019/3/7
 */
public class MonthPagerAdapter extends PagerAdapter {

    private int mCount;//最小日期 到 最大日期

    //计算方式：以 月/周 为单位


    /**
     * 返回item总数
     * @return
     */
    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }


}
