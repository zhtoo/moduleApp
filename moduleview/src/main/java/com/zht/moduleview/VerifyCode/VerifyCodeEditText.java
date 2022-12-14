package com.zht.moduleview.VerifyCode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

/**
 * Created by ZhangHaitao on 2019/4/3
 * https://www.likecs.com/show-204240801.html#sc=818
 * https://github.com/AdolphL/APwidget/blob/master/app/src/main/java/com/adolph/widget/DivideLineTextView.java
 */

public class VerifyCodeEditText extends View {

    private static final String TAG = "VerifyCode";

    private StringBuffer mText = new StringBuffer();

    private boolean showCursor = true;
    private boolean cursorShowStatus = false;

    private int mVerifyCodeNum = 6;

    private OnInputCompleteListener mCompleteListener;

    private Paint mBoxPaint;
    private int boxColor = Color.parseColor("#FFFFFF");
    private float boxRadius = 20F;

    private Paint mCursorPaint;
    private int cursorColor = Color.parseColor("#000000");
    private int cursorWidth = 0;
    private int cursorHeight = 0;

    private TextPaint mTextPaint;
    private int mTextColor = Color.parseColor("#000000");
    private int mTextSize = 0;

    public VerifyCodeEditText(Context context) {
        this(context, null);
    }

    public VerifyCodeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBoxPaint = new Paint();
        mBoxPaint.setAntiAlias(true);
        mBoxPaint.setColor(boxColor);
        mBoxPaint.setStyle(Paint.Style.FILL);

        boxRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        mCursorPaint = new Paint();
        mCursorPaint.setAntiAlias(true);
        mCursorPaint.setColor(cursorColor);
        mCursorPaint.setStyle(Paint.Style.FILL);
        cursorWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources().getDisplayMetrics());
        mTextPaint.setTextSize(mTextSize);
    }

    // 返回true，设置View变成文本可编辑的状态。默认返回false。
    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI;
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;

        return new BaseInputConnection(this, true) {
            @Override
            public boolean commitText(CharSequence text, int newCursorPosition) {
                Log.e(TAG, String.format("commitText(%s, %d)", text.toString(), newCursorPosition));
                if (mText.length() < mVerifyCodeNum) {
                    mText.append(text);
                    postInvalidate();
                }
                if (mText.length() == mVerifyCodeNum) {
                    if (mCompleteListener != null) {
                        mCompleteListener.onComplete(mText.toString());
                    }
                }
                return true;
            }

            @Override
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                Log.e(TAG, String.format("deleteSurroundingText(%d, %d)", beforeLength, afterLength));
                int dLength = beforeLength - afterLength;
                if (dLength > 0 && mText.length() > 0 && (mText.length() - dLength > -1)) {
                    mText.delete(mText.length() - dLength, mText.length());
                    postInvalidate();
                }
                return true;
            }

            @Override
            public boolean sendKeyEvent(KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    Log.e(TAG, String.format("sendKeyEvent(%d)", event.getKeyCode()));
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DEL:
                            if (mText.length() > 0) {
                                deleteSurroundingText(mText.length(), mText.length() - 1);
                            }
                            break;
                        case KeyEvent.KEYCODE_0:
                            commitText("0", mText.length());
                            break;
                        case KeyEvent.KEYCODE_1:
                            commitText("1", mText.length());
                            break;
                        case KeyEvent.KEYCODE_2:
                            commitText("2", mText.length());
                            break;
                        case KeyEvent.KEYCODE_3:
                            commitText("3", mText.length());
                            break;
                        case KeyEvent.KEYCODE_4:
                            commitText("4", mText.length());
                            break;
                        case KeyEvent.KEYCODE_5:
                            commitText("5", mText.length());
                            break;
                        case KeyEvent.KEYCODE_6:
                            commitText("6", mText.length());
                            break;
                        case KeyEvent.KEYCODE_7:
                            commitText("7", mText.length());
                            break;
                        case KeyEvent.KEYCODE_8:
                            commitText("8", mText.length());
                            break;
                        case KeyEvent.KEYCODE_9:
                            commitText("9", mText.length());
                            break;
                    }
                }
                return true;
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setFocusableInTouchMode(true);
                setFocusable(true);
                requestFocus();
                cursorShowStatus = true;
                removeCallbacks(showCursorTask);
                postDelayed(showCursorTask, 500);
                postInvalidate();
                return true;
            case MotionEvent.ACTION_UP:
                try {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN);
                    imm.restartInput(this);
                } catch (Exception e) {

                }
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!hasFocus()) {
            cursorShowStatus = false;
            removeCallbacks(showCursorTask);
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "drawBox");
        Log.e(TAG, "getPaddingLeft:" + getPaddingLeft());
        Log.e(TAG, "getPaddingTop:" + getPaddingTop());
        Log.e(TAG, "getPaddingRight:" + getPaddingRight());
        Log.e(TAG, "getPaddingBottom:" + getPaddingBottom());
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();

        float boxWidth = width * 1.0f / mVerifyCodeNum;

        float size = Math.min(boxWidth, height);
        drawBox(canvas, width, height, boxWidth, size);
        drawCursor(canvas, width, height, boxWidth, size);
        drawText(canvas, width, height, boxWidth, size);
    }

    private void drawBox(Canvas canvas, int width, int height, float boxWidth, float size) {
        if (mVerifyCodeNum == 0) {
            return;
        }
        float centerY = getPaddingTop() + height / 2.0f;
        float startX = getPaddingLeft();
        for (int i = 0; i < mVerifyCodeNum; i++) {

            startX = getPaddingLeft() + i * boxWidth;
            float left = startX + (boxWidth - size) / 2.0f;
            float top = centerY - size / 2.0f;
            float right = startX + (boxWidth + size) / 2.0f;
            float bottom = centerY + size / 2.0f;

            GradientDrawable drawable = new GradientDrawable();
            drawable.setSize((int) size, (int) size);
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setColor(boxColor);
            drawable.setCornerRadius(boxRadius);
            //drawable.setCornerRadii(new float[]{});
            drawable.setBounds(
                    (int) left,
                    (int) top,
                    (int) right,
                    (int) bottom);
            drawable.draw(canvas);

//            canvas.drawRoundRect(
//                    left,
//                    top,
//                    right,
//                    bottom,
//                    boxRadius, boxRadius, mBoxPaint);
        }
    }

    private void drawCursor(Canvas canvas, int width, int height, float boxWidth, float size) {
        if (showCursor && cursorShowStatus) {
            if (mText.length() < mVerifyCodeNum) {
                float startX = getPaddingLeft() + mText.length() * boxWidth;
                float centerX = startX + boxWidth / 2.0f;
                float centerY = getPaddingTop() + height / 2.0f;
                if (cursorHeight == 0) {
                    cursorHeight = (int) (size * 0.65F);
                }

                float left = centerX - cursorWidth / 2.0f;
                float top = centerY - cursorHeight / 2.0f;
                float right = centerX + cursorWidth / 2.0f;
                float bottom = centerY + cursorHeight / 2.0f;

                GradientDrawable drawable = new GradientDrawable();
                drawable.setSize((int) size, (int) size);
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setColor(cursorColor);
                drawable.setCornerRadius(cursorWidth / 2.0f);
                //drawable.setCornerRadii(new float[]{});
                drawable.setBounds(
                        (int) left,
                        (int) top,
                        (int) right,
                        (int) bottom);
                drawable.draw(canvas);
            }
        }
    }

    private void drawText(Canvas canvas, int width, int height, float boxWidth, float size) {
        if (mVerifyCodeNum == 0 || mText.length() == 0) {
            return;
        }
        float centerY = getPaddingTop() + height / 2.0f;
        float fontBaseLine = getFontBaseLine(mTextPaint);
        float startX;
        for (int i = 0; i < mText.length(); i++) {
            startX = getPaddingLeft() + i * boxWidth;
            float centerX = startX + boxWidth / 2.0f;
            float fontWidth = getFontWidth(mTextPaint, String.valueOf(mText.charAt(i)));
            canvas.drawText(String.valueOf(mText.charAt(i)), centerX - fontWidth / 2.0f, centerY + fontBaseLine, mTextPaint);
        }

    }

    private Runnable showCursorTask = new Runnable() {
        @Override
        public void run() {
            cursorShowStatus = !cursorShowStatus;
            postInvalidate();
            postDelayed(showCursorTask, 500);
        }
    };

    public String getText() {
        return mText.toString();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(showCursorTask);
    }

    public void setCompleteListener(OnInputCompleteListener listener) {
        this.mCompleteListener = listener;
    }

    public interface OnInputCompleteListener {
        void onComplete(String text);
    }

    /**
     * @return 返回指定的文字基线相对于中心点的距离
     */
    public float getFontBaseLine(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return -(fm.ascent + fm.descent) / 2;
    }

    /**
     * @return 返回指定的文字宽度
     */
    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

}
