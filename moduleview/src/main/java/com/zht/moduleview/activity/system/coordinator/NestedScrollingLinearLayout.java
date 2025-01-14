package com.zht.moduleview.activity.system.coordinator;

import static androidx.core.view.ViewCompat.TYPE_TOUCH;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * @Date 2024/7/11 10:18
 * @Author zhanghaitao
 * @Description
 */
public class NestedScrollingLinearLayout extends LinearLayout implements NestedScrollingChild3 {

    private static final String TAG = "NestedScrolling";

    private NestedScrollingChildHelper mScrollingChildHelper;

    public NestedScrollingLinearLayout(Context context) {
        this(context, null);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView() {
        setNestedScrollingEnabled(true);
    }

    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastTouchX;
    private int mLastTouchY;
    private final int[] mScrollOffset = new int[2];
    final int[] mReusableIntPair = new int[2];

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent -> event");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent -> event");
        final int action = event.getActionMasked();
        boolean result = super.onTouchEvent(event);
//        Log.e(TAG, "onTouchEvent -> result:" + result );
        if (!result) {
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mInitialTouchX = mLastTouchX = (int) (event.getX() + 0.5f);
                    mInitialTouchY = mLastTouchY = (int) (event.getY() + 0.5f);
                    int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                    startNestedScroll(nestedScrollAxis, TYPE_TOUCH);
                }
                break;
                case MotionEvent.ACTION_MOVE: {
                    final int x = (int) (event.getX() + 0.5f);
                    final int y = (int) (event.getY() + 0.5f);
                    int dx = mLastTouchX - x;
                    int dy = mLastTouchY - y;
                    dispatchNestedPreScroll(
                            dx,
                            dy,
                            mReusableIntPair, mScrollOffset, TYPE_TOUCH
                    );
                    mLastTouchX = x - mScrollOffset[0];
                    mLastTouchY = y - mScrollOffset[1];

                    if (scrollByInternal(
                            dx,
                            dy,
                            event)) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    stopNestedScroll(TYPE_TOUCH);
                }
                break;

            }
        }
        return result;
    }

    boolean scrollByInternal(int x, int y, MotionEvent ev) {
        int unconsumedX = 0;
        int unconsumedY = 0;
        int consumedX = 0;
        int consumedY = 0;
        mReusableIntPair[0] = 0;
        mReusableIntPair[1] = 0;
        dispatchNestedScroll(consumedX, consumedY, unconsumedX, unconsumedY, mScrollOffset,
                TYPE_TOUCH, mReusableIntPair);
        boolean consumedNestedScroll = mReusableIntPair[0] != 0 || mReusableIntPair[1] != 0;
        return consumedNestedScroll || consumedX != 0 || consumedY != 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e(TAG, "onInterceptTouchEvent -> event");
        final int action = event.getActionMasked();
        boolean result = super.onInterceptTouchEvent(event);
        if (!result) {
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                    startNestedScroll(nestedScrollAxis, TYPE_TOUCH);
                }
                break;
            }
        }
        return result;
    }

    // NestedScrollingChild
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.e(TAG, "startNestedScroll -> axes:" + axes + " type:" + type);
        return getScrollingChildHelper().startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        Log.e(TAG, "stopNestedScroll -> type:" + type);
        getScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public final void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                           int dyUnconsumed, int[] offsetInWindow, int type, @NonNull int[] consumed) {
        Log.e(TAG, "dispatchNestedScroll -> dxConsumed:" + dxConsumed + " dyConsumed:" + dyConsumed + " dxUnconsumed:" + dxUnconsumed + " dyUnconsumed:" + dyUnconsumed + " offsetInWindow:" + offsetInWindow + " type:" + type + " consumed:" + consumed);
        getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow,
                                           int type) {
        Log.e(TAG, "dispatchNestedPreScroll -> dx:" + dx + " dy:" + dy + " consumed:" + consumed + " offsetInWindow:" + offsetInWindow + " dy:" + type);
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow,
                type);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }
}




