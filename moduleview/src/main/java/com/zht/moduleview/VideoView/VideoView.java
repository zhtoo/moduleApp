package com.zht.moduleview.VideoView;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by ZhangHaitao on 2019/3/26
 */
public class VideoView extends SurfaceView {

    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private TouchTarget mFirstTouchTarget;

    /**
     * 描述一个触摸的视图和它捕获的指针的id。
     * 此代码假定指针ID始终在0..31范围内，
     * 这样它就可以使用位域来跟踪哪些指针ID存在。
     * 当发生这种情况时，输入调度管道的较低层也使用相同的技巧，
     * 因此假设 应该在这里安全......
     */
    private static final class TouchTarget {
        private static final int MAX_RECYCLED = 32;
        private static final Object sRecycleLock = new Object[0];
        private static TouchTarget sRecycleBin;
        private static int sRecycledCount;

        public static final int ALL_POINTER_IDS = -1; // all ones

        // 触摸的子View
        public View child;

        // 指针捕获的指针ID的组合位掩码，用于捕获的所有指针。
        public int pointerIdBits;

        // TouchTarget列表中的下一个TouchTarget。
        public TouchTarget next;

        private TouchTarget() {
        }

        /**
         * 获取一个TouchTarget
         * @param child
         * @param pointerIdBits
         * @return
         */
        public static TouchTarget obtain(@NonNull View child, int pointerIdBits) {
            if (child == null) {
                throw new IllegalArgumentException("child must be non-null");
            }

            final TouchTarget target;
            synchronized (sRecycleLock) {
                if (sRecycleBin == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecycleBin;
                    sRecycleBin = target.next;
                    sRecycledCount--;
                    target.next = null;
                }
            }
            target.child = child;
            target.pointerIdBits = pointerIdBits;
            return target;
        }

        /**
         * 回收
         */
        public void recycle() {
            if (child == null) {
                throw new IllegalStateException("already recycled once");
            }

            synchronized (sRecycleLock) {
                if (sRecycledCount < MAX_RECYCLED) {
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount += 1;
                } else {
                    next = null;
                }
                child = null;
            }
        }
    }
    /**
     *  这里需要结合obtain进行分析。
     *  TouchTarget.obtain后
     *  创建target，此时sRecycleBin为空
     *
     *
     */


    /**
     * 将指定子项的触摸目标添加到列表（mFirstTouchTarget）的开头。确定目标子项尚不存在。
     */
    private TouchTarget addTouchTarget(@NonNull View child, int pointerIdBits) {
        //创建TouchTarget
        final TouchTarget target = TouchTarget.obtain(child, pointerIdBits);
        //将ViewGroup的mFirstTouchTarget设置为target的next中，
        //然后将mFirstTouchTarget设置为target
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;
    }


    /**
     * 清除所有触摸目标
     */
    private void clearTouchTargets() {
        TouchTarget target = mFirstTouchTarget;
        if (target != null) {
            do {
                TouchTarget next = target.next;
                target.recycle();
                target = next;
            } while (target != null);
            mFirstTouchTarget = null;
        }
    }

    /**
     * 取消并清除所有TouchTarget。
     */
    private void cancelAndClearTouchTargets(MotionEvent event) {
        if (mFirstTouchTarget != null) {
            boolean syntheticEvent = false;
            if (event == null) {
                final long now = SystemClock.uptimeMillis();
                event = MotionEvent.obtain(now, now,
                        MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
                syntheticEvent = true;
            }

            for (TouchTarget target = mFirstTouchTarget; target != null; target = target.next) {
//                resetCancelNextUpFlag(target.child);
//                dispatchTransformedTouchEvent(event, true, target.child, target.pointerIdBits);
            }
            clearTouchTargets();

            if (syntheticEvent) {
                event.recycle();
            }
        }
    }

    /**
     * 获取指定子视图的TouchTarget。
     */
    private TouchTarget getTouchTarget(@NonNull View child) {
        for (TouchTarget target = mFirstTouchTarget; target != null; target = target.next) {
            if (target.child == child) {
                return target;
            }
        }
        return null;
    }


    /**
     * 从consideration中删除指针ID。
     */
    private void removePointersFromTouchTargets(int pointerIdBits) {
        TouchTarget predecessor = null;
        TouchTarget target = mFirstTouchTarget;
        while (target != null) {
            final TouchTarget next = target.next;
            if ((target.pointerIdBits & pointerIdBits) != 0) {
                target.pointerIdBits &= ~pointerIdBits;
                if (target.pointerIdBits == 0) {
                    if (predecessor == null) {
                        mFirstTouchTarget = next;
                    } else {
                        predecessor.next = next;
                    }
                    target.recycle();
                    target = next;
                    continue;
                }
            }
            predecessor = target;
            target = next;
        }
    }

    private void cancelTouchTarget(View view) {
        TouchTarget predecessor = null;
        TouchTarget target = mFirstTouchTarget;
        while (target != null) {
            final TouchTarget next = target.next;
            if (target.child == view) {
                if (predecessor == null) {
                    mFirstTouchTarget = next;
                } else {
                    predecessor.next = next;
                }
                target.recycle();

                final long now = SystemClock.uptimeMillis();
                MotionEvent event = MotionEvent.obtain(now, now,
                        MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
                view.dispatchTouchEvent(event);
                event.recycle();
                return;
            }
            predecessor = target;
            target = next;
        }
    }


}
