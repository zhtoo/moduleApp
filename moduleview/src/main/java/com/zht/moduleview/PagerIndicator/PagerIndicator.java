package com.zht.moduleview.PagerIndicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author KCrason
 * @date 2018/1/23
 */
public class PagerIndicator extends DynamicPagerIndicator {

    public PagerIndicator(Context context) {
        super(context);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View createTabView(PagerAdapter pagerAdapter, final int position) {
        return new CustomPagerTabView(mContext);
    }
}
