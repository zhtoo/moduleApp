package com.zht.common.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zht.common.R;
import com.zht.common.base.BaseApplication;

/**
 * Created by ZhangHaitao on 2018/8/30.
 * Toast管理类
 * 1、要实现Toast展示时间为一次Toast.show()的时间，
 * 需要对toast做非空判断，不为空直接setText()即可。
 */
public class ToastUtil {

    private static Toast toast;

    private static ToastUtil mInstance;

    private static int SHORT_BOTTOM = 10;
    private static int SHORT_CENTER = 11;
    private static int SHORT_TOP = 12;
    private static int LONG_BOTTOM = 20;
    private static int LONG_CENTER = 21;
    private static int LONG_TOP = 22;

    private ToastUtil() {
    }

    /**
     * 单例模式
     */
    public static ToastUtil getInstance() {
        if (mInstance == null) {
            synchronized (ToastUtil.class) {
                mInstance = new ToastUtil();
            }
        }
        return mInstance;
    }

    private static void showToast(String msg, int showType) {
        if (BaseApplication.getAppContext() != null) {
            //取消当前的Toast
            cancel();
            //根据类型设置时长
            if (showType < 20) {
                toast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_LONG);
            }
            //在这里重新再setText(msg)是因为再小米手机中，会再msg前面加上应用名称。
            toast.setText(msg);
            //根据类型设置位置
            int gravityType = showType % 10;
            if (gravityType == 0) {
                // Toast默认在底部显示，还原成Toast默认底部状态
                toast.setGravity(Gravity.BOTTOM, 0, dip2px(BaseApplication.getAppContext(), 64));
            } else if (gravityType == 1) {
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else if (gravityType == 2) {
                toast.setGravity(Gravity.TOP, 0, 0);
            }
            toast.show();
        }
    }

    /**
     * 设置默认的Toast
     *
     * @param msg
     */
    public static void showToast(String msg) {
        getInstance().showToast(msg, SHORT_BOTTOM);
    }


    /**
     * 设置带有图片的Toast
     *
     * @param msg
     */
    public static void showImageToast(String msg, @DrawableRes int resId) {
        cancel();
        toast = Toast.makeText(BaseApplication.getAppContext(), msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        toastView.setOrientation(LinearLayout.HORIZONTAL);
        toastView.setGravity(Gravity.CENTER_VERTICAL);
        //加入自己的图标
        ImageView imageCodeProject = new ImageView(BaseApplication.getAppContext());
        imageCodeProject.setImageResource(resId);
        //将图标设置在文字前面
        toastView.addView(imageCodeProject, 0);
        //重新布局Text
        TextView message = toastView.findViewById(android.R.id.message);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) message.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        message.setLayoutParams(layoutParams);

        //在这里重新再setText(msg)是因为再小米手机中，会再msg前面加上应用名称。
        toast.setText(msg);
        toast.show();
    }


    /**
     * 设置自定义的Toast
     *
     * @param msg
     */
    public static void showCustomerToast(String msg) {

        LinearLayout layout = new LinearLayout(BaseApplication.getAppContext());
        //设置LinearLayout属性(宽和高)
        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //设置边距
        layoutParams.setMargins(0, 0, 0, 0);
        //设置剧中
        layout.setGravity(Gravity.CENTER);
        //将以上的属性赋给LinearLayout
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(0xBB64C0FF);

        ImageView image = new ImageView(BaseApplication.getAppContext());
        image.setImageResource(R.mipmap.ic_launcher);
        TextView title = new TextView(BaseApplication.getAppContext());
        title.setText("完全自定义Toast");
        title.setTextColor(0xBB000000);
        TextView text = new TextView(BaseApplication.getAppContext());
        text.setText(msg);
        text.setTextColor(0xBB000000);

        layout.addView(image, 0);
        layout.addView(title, 1);
        layout.addView(text, 2);

        cancel();
        toast = new Toast(BaseApplication.getAppContext());
        toast.setGravity(Gravity.CENTER, 0, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /**
     * 短时间显示Toast居中
     *
     * @param msg 显示的内容-字符串
     */
    public static void showToastCenter(String msg) {
        ToastUtil.getInstance().showToast(msg, SHORT_CENTER);
    }

    /**
     * 短时间显示Toast居上
     *
     * @param msg 显示的内容-字符串
     */
    public static void showToastTop(String msg) {
        ToastUtil.getInstance().showToast(msg, SHORT_TOP);
    }

    /**
     * 长时间显示Toast居下
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToast(String msg) {
        ToastUtil.getInstance().showToast(msg, LONG_BOTTOM);
    }

    /**
     * 长时间显示Toast居中
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastCenter(String msg) {
        ToastUtil.getInstance().showToast(msg, LONG_CENTER);
    }

    /**
     * 长时间显示Toast居上
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastTop(String msg) {
        ToastUtil.getInstance().showToast(msg, LONG_TOP);
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
