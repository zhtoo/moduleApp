package com.zht.moduleview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by ZhangHaitao on 2019/11/22
 */
public abstract class BaseCustomizeView extends View {

    private PaintFlagsDrawFilter mDrawFilter;//Paint 绘制过滤器

    public BaseCustomizeView(Context context) {
        this(context, null);
    }

    public BaseCustomizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Paint 绘制过滤器
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas设置抗锯齿
        canvas.setDrawFilter(mDrawFilter);
    }

    /**
     * @return 默认宽度，改为抽象方法是为了让继承类必须定义默认值
     */
    public abstract int getDefaultWidth();

    /**
     * @return 默认高度，改为抽象方法是为了让继承类必须定义默认值
     */
    public abstract int getDefaultHeight();

    /**
     * 测量宽度
     *
     * @param widthMeasureSpec
     * @return
     */
    public int measureWidth(int widthMeasureSpec) {
        int measureValue = getMeasureValue(widthMeasureSpec);
        if (measureValue == -1) {
            return getDefaultWidth();
        }
        return measureValue;
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    public int measureHeight(int heightMeasureSpec) {
        int measureValue = getMeasureValue(heightMeasureSpec);
        if (measureValue == -1) {
            return getDefaultHeight();
        }
        return measureValue;
    }

    /**
     * @return 测量值
     */
    private int getMeasureValue(int measureSpec) {
        int mSpecMode = MeasureSpec.getMode(measureSpec);
        int mSpecSize = MeasureSpec.getSize(measureSpec);
        //这里是为了解决在Listview、ScrollView、RecyclerView的嵌套
        if (mSpecMode == MeasureSpec.AT_MOST ||
                mSpecMode == MeasureSpec.UNSPECIFIED) {
            return -1; //默认-1,此处需要自己定义默认宽高
        } else {
            return mSpecSize;
        }
    }

    /**
     * @return dp对应的px
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getContext().getResources().getDisplayMetrics());
    }

    /**
     * @return sp对应的px
     */
    public int sp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getContext().getResources().getDisplayMetrics());
    }

    /**
     * @return 返回指定的文字宽度
     */
    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    /**
     * @return 返回指定的文字基线相对于中心点的距离
     */
    public float getFontBaseLine(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return -(fm.ascent + fm.descent) / 2;
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

}
