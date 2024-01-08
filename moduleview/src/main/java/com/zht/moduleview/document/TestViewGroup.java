package com.zht.moduleview.document;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Date 2023/11/7 15:38
 * @Author zhanghaitao
 * @Description
 */
public class TestViewGroup extends ViewGroup {


    public TestViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top,int right, int bottom) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**
         * {@link android.view.ViewGroup.dispatchTransformedTouchEvent}
         */
        return super.dispatchTouchEvent(ev);
    }


    private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel,
                                                  View child, int desiredPointerIdBits) {

        return false;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
