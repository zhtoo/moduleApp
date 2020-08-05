package com.zht.moduleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.zht.moduleview.R;


/**
 * Created by ZhangHaitao on 2020/7/30
 */
public class RatioImageView extends AppCompatImageView {

    private String mRatio;
    private String mTarget;
    private float mAspectRatio = 0F;


    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView, defStyleAttr, 0);

        mRatio = a.getString(R.styleable.RatioImageView_layout_dimensionRatio);

        a.recycle();
        init();
    }

    private void init() {
        if (mRatio == null) {
            return;
        }
        if (mRatio.trim().length() == 0) {
            return;
        }

        try {
            int index = mRatio.indexOf(",");
            if (index > 0) {
                mTarget = mRatio.substring(0, index);
                mRatio = mRatio.substring(index + 1);
            }
            index = mRatio.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("layout_dimensionRatio 格式正确。示例：120:250 、 w,120:250 、 h,120:250");
            }
            int targetWidth = Integer.parseInt(mRatio.substring(0, index));
            int targetHeight = Integer.parseInt(mRatio.substring(index + 1));
            mAspectRatio = targetWidth * 1.0F / targetHeight;
        } catch (Exception e) {
            throw new IllegalArgumentException("layout_dimensionRatio 格式正确。示例：120:250 、 w,120:250 、 h,120:250");
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (TextUtils.isEmpty(mRatio)) {
            return;
        }
        if (mAspectRatio == 0) {
            return;
        }
        int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == View.MeasureSpec.AT_MOST || widthSpecMode == View.MeasureSpec.UNSPECIFIED) {
            widthSpecSize = (int) (mAspectRatio * heightSpecSize);
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST || heightSpecMode == View.MeasureSpec.UNSPECIFIED) {
            heightSpecSize = (int) (widthSpecSize / mAspectRatio);
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }


}
