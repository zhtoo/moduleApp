package com.zht.moduleview.document;

import android.view.View;

import androidx.annotation.NonNull;


/**
 * Describes a touched view and the ids of the pointers that it has captured.
 * 描述被触摸的view和它捕获的指针的id。
 *
 * This code assumes that pointer ids are always in the range 0..31 such that
 * it can use a bitfield to track which pointer ids are present.
 * 此代码假设指针id始终在 0..31 范围内，以便它可以使用位字段来跟踪存在的指针id。
 * As it happens, the lower layers of the input dispatch pipeline also use the
 * same trick so the assumption should be safe here...
 * 当它发生的时候，输入调度管道的较低层也使用相同的技巧，因此这里的假设应该是安全的......
 */
public   final class TouchTarget {
    private static final int MAX_RECYCLED = 32;
    private static final Object sRecycleLock = new Object[0];
    private static TouchTarget sRecycleBin;
    private static int sRecycledCount;

    public static final int ALL_POINTER_IDS = -1; // all ones

    // The touched child view.
    public View child;

    // The combined bit mask of pointer ids for all pointers captured by the target.
    public int pointerIdBits;

    // The next target in the target list.
    public TouchTarget next;

    private TouchTarget() {
    }

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
