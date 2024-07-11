package com.zht.moduleview.activity.system.coordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.zht.moduleview.R;


public class CustomBehavior extends CoordinatorLayout.Behavior<View> {

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (child.getId() == R.id.child) {
            return dependency.getId() == R.id.dependency;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        //根据dependency的位置，btn
        int totalWidth = ((View) dependency.getParent()).getWidth();
        int totalHeight = ((View) dependency.getParent()).getHeight();

        int top = dependency.getTop();
        int left = dependency.getLeft();

        int x = totalWidth - left - child.getWidth();
        int y = totalHeight - top - child.getHeight();

        setPosition(child, x, y);
        return true;
    }

    private void setPosition(View v, int x, int y) {
        v.layout(x, y, x + v.getWidth(), y + v.getHeight());
    }


}
