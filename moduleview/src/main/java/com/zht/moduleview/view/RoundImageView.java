package com.zht.moduleview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.zht.moduleview.R;


/**
 * Created by zhanghaitao on 2017/5/28.
 * 基于CircleImageView修改的圆角矩形
 */

public class RoundImageView extends AppCompatImageView {

    private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_CORNER_RADIUS = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();

    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final Paint mFillPaint = new Paint();
    //可以通过xml的属性来设置
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mFillColor = DEFAULT_FILL_COLOR;
    private boolean mBorderOverlay;
    //需要加载的图片
    private Bitmap mBitmap;
    //图片图形
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;


    private int mBitmapHeight;

    private ColorFilter mColorFilter;
    private boolean mReady;
    private boolean mSetupPending;
    private String mDimensionRatio;

    public int getRadius() {
        return mCornerRadius;
    }

    public void setRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        invalidate();
    }

    //圆角半径
    private int mCornerRadius;


    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyle, 0);

        mBorderWidth = a.getDimensionPixelSize(R.styleable.RoundImageView_riv_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = a.getColor(R.styleable.RoundImageView_riv_border_color, DEFAULT_BORDER_COLOR);
        mBorderOverlay = a.getBoolean(R.styleable.RoundImageView_riv_border_overlay, DEFAULT_BORDER_OVERLAY);
        mFillColor = a.getColor(R.styleable.RoundImageView_riv_fill_color, DEFAULT_FILL_COLOR);
        mCornerRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_riv_corner_radius, DEFAULT_CORNER_RADIUS);
        mDimensionRatio = a.getString(R.styleable.RoundImageView_riv_dimensionRatio);
        a.recycle();
        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }

        checkRatio();
    }

    private String mRatio;
    private String mTarget;
    private float mAspectRatio = 0F;

    private void checkRatio() {
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


    @Override
    public ImageView.ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(SCALE_TYPE);
       /* if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }*/
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        //  RectF rectBorder = new RectF(0, 0, mBitmapWidth, mBitmapHeight);
        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawRoundRect(rect,
                    mCornerRadius, //x轴的半径
                    mCornerRadius, //y轴的半径
                    mFillPaint);
        }
        canvas.drawRoundRect(rect,
                mCornerRadius, //x轴的半径
                mCornerRadius, //y轴的半径
                mBitmapPaint);
        //画边线
        if (mBorderWidth != 0) {
            canvas.drawRoundRect(rect,
                    mCornerRadius, //x轴的半径
                    mCornerRadius, //y轴的半径
                    mBorderPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }

        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    public void setBorderColorResource(@ColorRes int borderColorRes) {
        setBorderColor(getContext().getResources().getColor(borderColorRes));
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == mFillColor) {
            return;
        }

        mFillColor = fillColor;
        mFillPaint.setColor(fillColor);
        invalidate();
    }

    public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }

        mBorderWidth = borderWidth;
        setup();
    }

    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    public void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) {
            return;
        }

        mBorderOverlay = borderOverlay;
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = uri != null ? getBitmapFromDrawable(getDrawable()) : null;
        setup();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }

        mColorFilter = cf;
        mBitmapPaint.setColorFilter(mColorFilter);
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (mBitmap == null) {
            invalidate();
            return;
        }

        /*
        if (oldWidth != mDrawableWidth || oldHeight != mDrawableHeight) {
            requestLayout();
        }
        invalidate();
         */

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);


        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形线帽
        mBorderPaint.setStrokeJoin(Paint.Join.ROUND);//圆弧

        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setColor(mFillColor);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(0, 0, getWidth(), getHeight());

        mDrawableRect.set(mBorderRect);
        if (!mBorderOverlay) {
            mDrawableRect.inset(mBorderWidth, mBorderWidth);
        }

        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}
