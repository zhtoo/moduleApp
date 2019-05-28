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
    private View main;//展示界面

    private int dragWidth;
    private int dragHeight;
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
        main = getChildAt(1); // 获取主界面容器
        // 测量菜单
        dragView.measure(widthMeasureSpec, heightMeasureSpec);
        // 测量主界面
        main.measure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec)
                + getPaddingBottom() + getPaddingTop();
        Log.e(TAG, "width: " + width);
        Log.e(TAG, "height: " + height);

        LayoutParams dragLP = dragView.getLayoutParams();
        dragWidth = dragLP.width;
        dragHeight = dragLP.height;
        Log.e(TAG, "dragWidth: " + dragWidth);
        Log.e(TAG, "dragHeight: " + dragHeight);

        LayoutParams mainLP = main.getLayoutParams();
        mainWidth = mainLP.width;
        mainHeight = mainLP.height;
        if (mainWidth == LayoutParams.MATCH_PARENT) {
            mainWidth = width;
        }
        if (mainHeight <= 0) {
            mainHeight =  dp2px(300);
        }
        Log.e(TAG, "mainWidth: " + mainWidth);
        Log.e(TAG, "mainHeight: " + mainHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        viewLeft = left;
        viewTop = top;
        viewRight = right;
        viewBottom = bottom;

        // 菜单的left位置为菜单的宽的负数
        dragLeft = (right - left) / 2 - dragWidth / 2;
        dragTop = bottom - mainHeight - dragHeight;
        dragRight = dragLeft + dragWidth;
        // 菜单的bottom位置为容器的高
        dragBottom = bottom - mainHeight;

//        Log.e(TAG, "dragLeft:" + dragLeft);
//        Log.e(TAG, "dragTop:" + dragTop);
//        Log.e(TAG, "dragRight:" + dragRight);
//        Log.e(TAG, "dragBottom:" + dragBottom);
        dragView.layout(dragLeft, dragTop, dragRight, dragBottom);

        // 对主界面进行排版
        int mainLeft = left;
        int mainTop = bottom - mainHeight;
        int mainRight = right;
        int mainBottom = bottom;

//        Log.e(TAG, "mainLeft:" + mainLeft);
//        Log.e(TAG, "mainTop:" + mainTop);
//        Log.e(TAG, "mainRight:" + mainRight);
//        Log.e(TAG, "mainBottom:" + mainBottom);
        main.layout(mainLeft, mainTop, mainRight, mainBottom);
//        main.requestLayout();

//        Log.e(TAG, "=============================================");
    }


    private int downY;
    private int downX;

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

                if (downX >= dragLeft && downX <= dragRight
                        && downY >= dragTop && downY <= dragBottom
                        ) {
                    Log.e(TAG, "onInterceptTouchEvent: " + true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX = Math.abs((int) (ev.getX() - downX));
                int distanceY = Math.abs((int) (ev.getY() - downY));
                Log.e(TAG, "distanceX < distanceY: " + (distanceX < distanceY));
                Log.e(TAG, "downX >= dragLeft: " + (downX >= dragLeft));
                Log.e(TAG, "downX <= dragRight: " + (downX <= dragRight));
                Log.e(TAG, "downY <= dragTop: " + (downY <= dragTop));
                Log.e(TAG, "downY >= dragBottom: " + (downY >= dragBottom));
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


    boolean canMove = false;

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
                Log.e(TAG, "ACTION_DOWN");
                downY = (int) event.getY();
                downX = (int) event.getX();
                if (downX >= dragLeft
                        && downX <= dragRight
                        && downY >= dragTop
                        && downY <= dragBottom
                        ) {
                    canMove = true;

                } else {
                    canMove = false;
                }
                Log.e(TAG, "canMove: " + canMove);
                return canMove;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                if (canMove) {
                    int fingerMoveDistanceY = (int) event.getY() - downY;    // 手指移动的距离
                    int top = dragTop + fingerMoveDistanceY;
                    if (top <= viewTop) {
                        top = viewTop;
                    }
                    if (top >= viewBottom - dragHeight) {
                        top = viewBottom - dragHeight;
                    }
                    dragBottom = top + dragHeight;
                    dragView.layout(dragLeft, top, dragRight, dragBottom);
                    if (viewBottom - dragBottom < mainHeight) {
                        main.layout(viewLeft, dragBottom, viewRight, dragBottom + mainHeight);
                    } else {
                        main.layout(viewLeft, dragBottom, viewRight, viewBottom);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                dragTop = dragBottom - dragHeight;
                break;
        }
        boolean b = super.onTouchEvent(event);
        return b;
    }


//    /**
//     * 获取屏幕宽度像素
//     *
//     * @return
//     */
//    public int getWidthPixels() {
//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        Configuration cf = getContext().getResources().getConfiguration();
//        int ori = cf.orientation;
//        if (ori == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
//            return displayMetrics.heightPixels;
//        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
//            return displayMetrics.widthPixels;
//        }
//        return 0;
//    }

//    /**
//     * 获取屏幕高度像素
//     *
//     * @return
//     */
//    public int getHeightPixels() {
//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        Configuration cf = getContext().getResources().getConfiguration();
//        int ori = cf.orientation;
//        if (ori == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
//            return displayMetrics.widthPixels;
//        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
//            return displayMetrics.heightPixels;
//        }
//        return 0;
//    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                1,
                dp,
                getContext().getResources().getDisplayMetrics());

    }

}
