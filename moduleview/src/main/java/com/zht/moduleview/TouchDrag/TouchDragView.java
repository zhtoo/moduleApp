package com.zht.moduleview.TouchDrag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangHaitao on 2019/5/27
 *
 * @description: 1、必须是 match_parent
 * 2、必须在最顶层
 * 保证View能够抢到事件
 */
public class TouchDragView extends ViewGroup {

    public static final String TAG = "TouchDragView";

    public TouchDragView(Context context) {
        this(context, null);
    }

    public TouchDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View dragView;//拖动按钮
    private View titleView;//标题按钮
    private View main;//展示界面

    private int dragWidth;
    private int dragHeight;
    private int titleWidth;
    private int titleHeight;
    private int mainWidth;
    private int mainHeight;

    private int dragLeft;
    private int dragTop;
    private int dragRight;
    private int dragBottom;

    private int viewLeft;
    private int viewTop;
    private int viewRight;
    private int viewBottom;

    private int titleLeft;
    private int titleTop;
    private int titleRight;
    private int titleBottom;


    private int downY;
    private int downX;


    boolean canMove = false;
    boolean isMove = false;


    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    // 测量容器自己的宽高
        dragView = getChildAt(0); // 获取菜单容器
        titleView = getChildAt(1); // 获取主界面容器
        main = getChildAt(2); // 获取主界面容器

        dragView.measure(widthMeasureSpec, heightMeasureSpec);
        titleView.measure(widthMeasureSpec, heightMeasureSpec);
        main.measure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec)
                + getPaddingBottom() + getPaddingTop();

        LayoutParams dragLP = dragView.getLayoutParams();
        dragWidth = dragLP.width;
        dragHeight = dragLP.height;

        LayoutParams titleLP = titleView.getLayoutParams();
        titleWidth = titleLP.width;
        titleHeight = titleLP.height;

        LayoutParams mainLP = main.getLayoutParams();
        mainWidth = mainLP.width;
        mainHeight = mainLP.height;
        if (mainWidth == LayoutParams.MATCH_PARENT) {
            mainWidth = width;
        }
        if (mainHeight <= 0) {
            mainHeight = dp2px(300);
        }

        Log.e(TAG, "mainHeight: " + mainHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (!isMove) {
            viewLeft = left;
            viewTop = top;
            viewRight = right;
            viewBottom = bottom;

            dragLeft = (right - left) / 2 - dragWidth / 2;
            dragTop = bottom - mainHeight - dragHeight - titleHeight;
            dragRight = dragLeft + dragWidth;
            dragBottom = dragTop + dragHeight;

            dragView.layout(dragLeft, dragTop, dragRight, dragBottom);

            titleLeft = left;
            titleTop = dragBottom;
            titleRight = right;
            titleBottom = dragBottom + titleHeight;
            titleView.layout(titleLeft, titleTop, titleRight, titleBottom);

        } else {
            dragView.layout(dragLeft, dragTop, dragRight, dragBottom);
            titleView.layout(titleLeft, titleTop, titleRight, titleBottom);
        }

        int mainLeft = viewLeft;
        int mainTop = titleBottom;
        int mainRight = viewRight;
        int mainBottom = viewBottom;
        main.layout(mainLeft, mainTop, mainRight, mainBottom);

    }

    /**
     * 滑动拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                downX = (int) ev.getX();

                if (inDragRange()) {
//                    Log.e(TAG, "onInterceptTouchEvent: " + true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = Math.abs((int) (ev.getX() - downX));
                int distanceY = Math.abs((int) (ev.getY() - downY));
                if (distanceX < distanceY
                        && downX >= dragLeft
                        && downX <= dragRight
                        && downY >= dragTop
                        && downY <= dragBottom
                        ) {
                    Log.e(TAG, "onInterceptTouchEvent: " + true);
                    // 如果水平移动距离比垂直移动距离大，则认为是水平移动，把事件拦截，不让ScrollView使用
                    return true;    // true代表拦截事件
                }
                break;
        }
        boolean b = super.onInterceptTouchEvent(ev);

        return b;
    }


    /**
     * 处理点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "ACTION_DOWN");
                downY = (int) event.getY();
                downX = (int) event.getX();
                if (inDragRange()) {
                    canMove = true;
                } else {
                    canMove = false;
                }
//                Log.e(TAG, "canMove: " + canMove);
                return canMove;
            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG, "ACTION_MOVE");
                if (canMove) {
                    if (!isMove) {
                        isMove = true;
                    }
                    int fingerMoveDistanceY = (int) event.getY() - downY;    // 手指移动的距离
                    int top = dragTop + fingerMoveDistanceY;
                    if (top <= viewTop) {
                        top = viewTop;
                    }
                    if (top >= viewBottom - dragHeight - titleHeight) {
                        top = viewBottom - dragHeight - titleHeight;
                    }
                    dragBottom = top + dragHeight;
                    dragView.layout(
                            dragLeft,
                            top,
                            dragRight,
                            dragBottom);
                    titleView.layout(
                            titleLeft,
                            dragBottom,
                            titleRight,
                            dragBottom + titleHeight);
                    if (viewBottom - dragBottom < mainHeight) {
                        main.layout(viewLeft,
                                dragBottom + titleHeight,
                                viewRight,
                                dragBottom + mainHeight + titleHeight);
                    } else {
                        main.layout(
                                viewLeft,
                                dragBottom + titleHeight,
                                viewRight,
                                viewBottom);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "ACTION_UP");
                dragTop = dragBottom - dragHeight;
                titleTop = dragBottom;
                titleBottom = titleTop + titleHeight;
                canMove = false;
                break;
        }
        boolean b = super.onTouchEvent(event);
        return b;
    }


    private boolean inDragRange() {
        if (downX >= dragLeft && downX <= dragRight
                && downY >= dragTop && downY <= dragBottom) {
            return true;
        }
        if (downX >= titleLeft
                && downX <= titleRight
                && downY >= titleTop
                && downY <= titleBottom) {
            return true;
        }
        return false;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                1,
                dp,
                getContext().getResources().getDisplayMetrics());

    }

}
