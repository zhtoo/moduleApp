package com.zht.modulehome.activity.camera2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * @Date 2024/11/27 15:03
 * @Author zhanghaitao
 * @Description
 */
public class AutoFitSurfaceView extends SurfaceView {

    private final static String TAG = "AutoFitSurfaceView";

    public AutoFitSurfaceView(Context context) {
        this(context,null);
    }

    public AutoFitSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoFitSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float aspectRatio = 0f;

    /**
     * 设置此视图的宽高比。视图的大小将基于从参数中计算的比率来测量。
     *
     * @param width  相机水平分辨率
     * @param height 相机垂直分辨率
     */
    public void setAspectRatio(int width, int  height) {
//        if(width > 0 && height > 0) { "Size cannot be negative" }
//
//        aspectRatio = width.toFloat() / height.toFloat()
//        holder.setFixedSize(width, height)
//        requestLayout()
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val width = MeasureSpec.getSize(widthMeasureSpec)
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        if (aspectRatio == 0f) {
//            setMeasuredDimension(width, height)
//        } else {
//
//            // Performs center-crop transformation of the camera frames
//            val newWidth: Int
//            val newHeight: Int
//            val actualRatio = if (width > height) aspectRatio else 1f / aspectRatio
//            if (width < height * actualRatio) {
//                newHeight = height
//                newWidth = (height * actualRatio).roundToInt()
//            } else {
//                newWidth = width
//                newHeight = (width / actualRatio).roundToInt()
//            }
//
//            XLog.d(TAG, "Measured dimensions set: $newWidth x $newHeight")
//            setMeasuredDimension(newWidth, newHeight)
//        }
//    }
//
//    companion object {
//        private val TAG = AutoFitSurfaceView::class.java.simpleName
//    }
}
