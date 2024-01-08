package com.zht.moduleview.document;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Date 2023/11/9 15:49
 * @Author zhanghaitao
 * @Description
 */
public class TestView extends View {

    public TestView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
