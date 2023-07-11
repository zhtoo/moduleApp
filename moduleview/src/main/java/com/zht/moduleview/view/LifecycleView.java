package com.zht.moduleview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

/**
 * @Date 2023/7/10 14:37
 * @Author zhanghaitao
 * @Description
 */
public class LifecycleView extends View {

    private static final String TAG = "LifecycleView";

    public LifecycleView(Context context) {
        super(context);
    }

    public LifecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LifecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(context instanceof AppCompatActivity){
            Lifecycle lifecycle = ((AppCompatActivity) context).getLifecycle();

        }
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        Log.e(TAG,"onFinishInflate");
        super.onFinishInflate();
    }

    /**
     * View测量控件大小，ViewGroup测量所有子控件大小后确定自身大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * View被分配到的确定大小和位置时调用
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG,"onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * view的大小发生变化时调用
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * view绘制内容
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG,"onDraw");
        super.onDraw(canvas);
    }

    /**
     * 有按键按下后调用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG,"onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 按键按下后弹起时调用
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e(TAG,"onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 轨迹球事件
     */
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Log.e(TAG,"onTrackballEvent");
        return super.onTrackballEvent(event);
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }

    /**
     * View获取或失去焦点时调用
     */
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Log.e(TAG,"onFocusChanged");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    /**
     * 当窗口包含的view获取或失去焦点时调用
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.e(TAG,"onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * view被附着到一个窗口时调用
     */
    @Override
    protected void onAttachedToWindow() {
        Log.e(TAG,"onAttachedToWindow");
        super.onAttachedToWindow();
    }

    /**
     *  view离开附着的窗口时调用
     */
    @Override
    protected void onDetachedFromWindow() {
        Log.e(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    /**
     * 当窗口中包含的可见的view发生变化时调用
     */
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.e(TAG,"onVisibilityChanged");
        super.onVisibilityChanged(changedView, visibility);
    }

}
