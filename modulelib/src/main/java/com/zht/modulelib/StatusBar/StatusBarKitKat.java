package com.zht.modulelib.StatusBar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zht.modulelib.R;

/**
 * @Date 2018/5/23 16:07
 * @Author ZhangHaitao
 * @Description 基于Android4.4版本的状态栏工具类
 * <item name="android:windowTranslucentStatus">true</item>
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class StatusBarKitKat {
    private static final String TAG = "StatusBarKitKat";
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN = "marginTop";

    /**
     * 显示/隐藏状态栏
     *
     * @param activity
     * @param isVisibility
     */
    public static void setStatusBarVisibility(Activity activity, boolean isVisibility) {
        Window window = activity.getWindow();
        //设置Window为透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //获取根布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);

        ((ViewGroup) window.getDecorView()).getChildAt(0);
        if (mContentView == null) return;
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);
        // 获取父布局
        View mContentChild = mContentView.getChildAt(0);
        //获取假状态栏
        View fakeView = mContentView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView == null) {
            fakeView = new View(activity);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            layoutParams.gravity = Gravity.TOP;
            fakeView.setLayoutParams(layoutParams);
            fakeView.setBackgroundColor(0x00000000);
            fakeView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
            //添加到根布局
            mContentView.addView(fakeView);
        }
        fakeView.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        if (isVisibility) {
         addMargin(mContentChild,statusBarHeight);
        } else {
            removeMargin(mContentChild,statusBarHeight);
        }
    }

    /**
     * 设置全屏
     *
     * @param activity
     * @param isFullScreen
     */
    public static void setFullScreen(Activity activity, boolean isFullScreen) {
        Window window = activity.getWindow();
        //设置Window为透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);
        if (isFullScreen) {
            removeMargin(mContentChild,statusBarHeight);
        } else {
            addMargin(mContentChild,statusBarHeight);
        }
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param activity
     * @param statusColor
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //获取根布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        if (mContentView == null) return;
        //获取父布局
        View mContentChild = mContentView.getChildAt(0);
        if (mContentChild == null) return;
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);
        //获取假状态栏
        View fakeView = mContentView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView == null) {
            fakeView = new View(activity);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            layoutParams.gravity = Gravity.TOP;
            fakeView.setLayoutParams(layoutParams);
            fakeView.setBackgroundColor(statusColor);
            fakeView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
            //添加到根布局
            mContentView.addView(fakeView);
            //添加MarginTop
            addMargin(mContentChild,statusBarHeight);
        }
        if (fakeView.getVisibility() == View.GONE) {
            fakeView.setVisibility(View.VISIBLE);
        }
        int color = ((ColorDrawable) fakeView.getBackground().mutate()).getColor();
        if (color != statusColor) {
            fakeView.setBackgroundColor(statusColor);
        }
        //不预留系统栏位置
        if (mContentChild != null) {
            mContentChild.setFitsSystemWindows(false);
        }
        // 如果在Activity中使用了ActionBar
        // 则需要再将布局与状态栏的高度跳到一个ActionBar的高度，
        // 否则内容会被ActionBar遮挡
        int action_bar_id = activity.getResources()
                .getIdentifier("action_bar", "id", activity.getPackageName());
        View view = activity.findViewById(action_bar_id);
        if (view != null) {
            TypedValue typedValue = new TypedValue();
            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
                mContentView = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                mContentView.setPadding(0, actionBarHeight, 0, 0);
            }
        }
    }

    /**
     * 添加父布局的Margin值
     * @param mContentChild   ((ViewGroup) window.getDecorView()).getChildAt(0);
     * @param statusBarHeight  状态栏的高度
     */
    private static void addMargin(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_MARGIN.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp =
                    (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_MARGIN);
        }
    }

    /**
     * 移除父布局的Margin值
     * @param mContentChild ((ViewGroup) window.getDecorView()).getChildAt(0);
     * @param statusBarHeight 状态栏的高度
     */
    private static void removeMargin(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (TAG_MARGIN.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

//    static void setStatusBarColor(Activity activity, int statusColor) {
//        Window window = activity.getWindow();
//        //设置Window为全透明
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
////        ViewGroup mContentView = (ViewGroup)window.getDecorView();
//
//        //获取父布局
//        View mContentChild = mContentView.getChildAt(0);
//
//        //获取状态栏高度
//        int statusBarHeight = getStatusBarHeight(activity);
//
//        //如果已经存在假状态栏则移除，防止重复添加
//        removeFakeStatusBarViewIfExist(activity);
//        //添加一个View来作为状态栏的填充
//        addFakeStatusBarView(activity, statusColor, statusBarHeight);
//
//        //设置子控件到状态栏的间距
//        addMarginTopToContentChild(mContentChild, statusBarHeight);
//        //不预留系统栏位置
//        if (mContentChild != null) {
//            mContentChild.setFitsSystemWindows(false);
//        }
//        // 如果在Activity中使用了ActionBar
//        // 则需要再将布局与状态栏的高度跳到一个ActionBar的高度，
//        // 否则内容会被ActionBar遮挡
//        int action_bar_id = activity.getResources()
//                .getIdentifier("action_bar", "id", activity.getPackageName());
//        View view = activity.findViewById(action_bar_id);
//        if (view != null) {
//            TypedValue typedValue = new TypedValue();
//            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
//                int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics());
//                mContentView = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
//                mContentView.setPadding(0, actionBarHeight, 0, 0);
//            }
//        }
//    }

    /**
     * 设置透明状态栏
     *
     * @param activity
     */
//    static void translucentStatusBar(Activity activity) {
//        Window window = activity.getWindow();
//        //设置Window为透明
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        ViewGroup mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
//        View mContentChild = mContentView.getChildAt(0);
//
//        //移除已经存在假状态栏则,并且取消它的Margin间距
//        removeFakeStatusBarViewIfExist(activity);
//        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
//        if (mContentChild != null) {
//            //fitsSystemWindow 为 false, 不预留系统栏位置.
//            mContentChild.setFitsSystemWindows(false);
//        }
//    }

//    private static void removeFakeStatusBarViewIfExist(Activity activity) {
//        Window window = activity.getWindow();
//        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
//        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
//        if (fakeView != null) {
//            mDecorView.removeView(fakeView);
//        }
//    }

//    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
//        Window window = activity.getWindow();
//        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
//
//        View mStatusBarView = new View(activity);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//        layoutParams.gravity = Gravity.TOP;
//        mStatusBarView.setLayoutParams(layoutParams);
//        mStatusBarView.setBackgroundColor(statusBarColor);
//        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
//
//        mDecorView.addView(mStatusBarView);
//        return mStatusBarView;
//    }




}
