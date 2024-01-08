package com.zht.moduleview.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @Date 2023/8/21 16:39
 * @Author zhanghaitao
 * @Description
 */
public class TouchGestureView extends ConstraintLayout implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

    private final static String TAG = "TouchGesture";
    //
    protected int mTouchSlop;
    protected int mMinVelocity;
    protected int mMaxVelocity;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private float currentScale = 0F;
    private boolean mIsScale = false;


    public TouchGestureView(@NonNull Context context) {
        super(context);
        initView(context);
    }


    public TouchGestureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TouchGestureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();

        gestureDetector = new GestureDetector(context, this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case (MotionEvent.ACTION_DOWN):
//                Log.d(TAG, "Action was DOWN");
//                break;
//            case (MotionEvent.ACTION_MOVE):
//                Log.d(TAG, "Action was MOVE");
//                break;
//            case (MotionEvent.ACTION_UP):
//                Log.d(TAG, "Action was UP");
//                break;
//            case (MotionEvent.ACTION_CANCEL):
//                Log.d(TAG, "Action was CANCEL");
//                break;
//            case (MotionEvent.ACTION_OUTSIDE):
//                Log.d(TAG, "Movement occurred outside bounds " +
//                        "of current screen element");
//                break;
//            default:
//                return super.dispatchTouchEvent(event);
//        }
//        return super.dispatchTouchEvent(event);
        return onTouchEvent(event);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case (MotionEvent.ACTION_DOWN):
//                Log.d(TAG, "Intercept Action was DOWN");
//                break;
//            case (MotionEvent.ACTION_MOVE):
//                Log.d(TAG, "Intercept Action was MOVE");
//                break;
//            case (MotionEvent.ACTION_UP):
//                Log.d(TAG, "Intercept Action was UP");
//                break;
//            case (MotionEvent.ACTION_CANCEL):
//                Log.d(TAG, "Intercept Action was CANCEL");
//                break;
//            case (MotionEvent.ACTION_OUTSIDE):
//                Log.d(TAG, "Movement occurred outside bounds " +
//                        "of current screen element");
//                break;
//            default:
//                return super.onInterceptTouchEvent(event);
//        }
//        return super.onInterceptTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MotionEvent copyEvent = MotionEvent.obtain(event);
        boolean result = scaleGestureDetector.onTouchEvent(event);

        if (mIsScale) {
            return true;
        }
        gestureDetector.onTouchEvent(copyEvent);

        if (scaleGestureDetector.onTouchEvent(event)) {
            result = true;
        } else {
            result = super.onTouchEvent(event);
        }
        return result;
    }


    @Override
    public boolean onScale(@NonNull ScaleGestureDetector detector) {
        currentScale *= detector.getScaleFactor();
        currentScale = Math.max(0.2F, Math.min(currentScale, 5F));
        Log.e(TAG, "onScale:" + currentScale);
        if (scaleListener != null) {
            scaleListener.onScale(currentScale);
        }
        return false;
    }


    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
//        Log.e(TAG, "onScaleBegin");
        mIsScale = true;
        currentScale = 1F;
        if (scaleListener != null) {
            return scaleListener.onScaleStart();
        }
        return true;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
//        Log.e(TAG, "onScaleEnd");
        mIsScale = false;
        currentScale = 0F;
        if (scaleListener != null) {
            scaleListener.onScaleEnd();
        }
    }


    /**
     * 按下监听
     */
    @Override
    public boolean onDown(@NonNull MotionEvent downEvent) {
        Log.e(TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent downEvent) {
        Log.e(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent event) {
        Log.e(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent downEvent, @NonNull MotionEvent event, float distanceX, float distanceY) {
//        Log.e(TAG, "onScroll");
        float dX = event.getX() - downEvent.getX();
        float dY = event.getY() - downEvent.getY();

        if (Math.abs(dX) > Math.abs(dY)) {
            if (dX < -mTouchSlop && Math.abs(distanceX) > mTouchSlop) { // 左滑
                if (flingListener != null) {
                    return flingListener.onLeftFling(this, downEvent, event);
                }
            } else if (dX > mTouchSlop && Math.abs(distanceX) > mTouchSlop) { // 右滑
                if (flingListener != null) {
                    return flingListener.onRightFling(this, downEvent, event);
                }
            }
        } else {
            if (dY < -mTouchSlop && distanceY > mTouchSlop) { // 上滑
                if (flingListener != null) {
                    return flingListener.onTopFling(this, downEvent, event);
                }
            } else if (dY > mTouchSlop && distanceY > mTouchSlop) { // 下滑

                if (flingListener != null) {
                    return flingListener.onBottomFling(this, downEvent, event);
                }
            }
        }

        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {
        Log.e(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(@NonNull MotionEvent downEvent, @NonNull MotionEvent event, float velocityX, float velocityY) {
//        Log.e(TAG, "onFling");
        float dX = event.getX() - downEvent.getX();
        float dY = event.getY() - downEvent.getY();
//        Log.e(TAG, "onFling: dX =" + dX);
//        Log.e(TAG, "onFling: dY =" + dY);
//        Log.e(TAG, "onFling: velocityX =" + velocityX);
//        Log.e(TAG, "onFling: velocityY =" + velocityY);

        if (Math.abs(dX) > Math.abs(dY)) {
            if (dX < -mTouchSlop && Math.abs(velocityX) > mMinVelocity) { // 左滑
                if (flingListener != null) {
                    return flingListener.onLeftFling(this, downEvent, event);
                }
            } else if (dX > mTouchSlop && Math.abs(velocityX) > mMinVelocity) { // 右滑

                if (flingListener != null) {
                    return flingListener.onRightFling(this, downEvent, event);
                }
            }
        } else {
            if (dY < -mTouchSlop && Math.abs(velocityY) > mMinVelocity) { // 上滑

                if (flingListener != null) {
                    return flingListener.onTopFling(this, downEvent, event);
                }
            } else if (dY > mTouchSlop && Math.abs(velocityY) > mMinVelocity) { // 下滑

                if (flingListener != null) {
                    return flingListener.onBottomFling(this, downEvent, event);
                }
            }
        }
        return false;
    }


    private OnFlingListener flingListener = new OnFlingListener() {
        @Override
        public boolean onLeftFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            Log.e(TAG, "左滑");
            return false;
        }

        @Override
        public boolean onRightFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            Log.e(TAG, "右滑");
            return false;
        }

        @Override
        public boolean onTopFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            Log.e(TAG, "上滑");
            return false;
        }

        @Override
        public boolean onBottomFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            Log.e(TAG, "下滑");
            return false;
        }
    };

    public void setFlingListener(OnFlingListener flingListener) {
        this.flingListener = flingListener;
    }

    public static class OnFlingListener {

        public boolean onLeftFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            return false;
        }

        public boolean onRightFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            return false;
        }

        public boolean onTopFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            return false;
        }

        public boolean onBottomFling(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            return false;
        }

        public boolean onFlingFinish(View view, MotionEvent downEvent, MotionEvent touchEvent) {
            return false;
        }
    }

    private OnScaleListener scaleListener;

    public void setScaleListener(OnScaleListener scaleListener) {
        this.scaleListener = scaleListener;
    }

    public static class OnScaleListener {
        public boolean onScaleStart() {
            return false;
        }

        public void onScaleEnd() {

        }

        public boolean onScale(float scale) {
            return false;
        }
    }

}
