package com.zht.moduleview.CustomKeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.ColorInt;

import com.zht.moduleview.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Date 2018/6/14
 * @Author ZhangHaitao
 * @Project CustomKeyboard
 * @Description
 * @Version
 */
public class CustomKeyboardView extends KeyboardView {


    private int padding;


    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        padding = dp2px(context, 3f);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        padding = dp2px(context, 3f);
    }

    private Keyboard mKeyBoard;

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        mKeyBoard = getKeyboard();
        List<Keyboard.Key> keys = null;
        if (mKeyBoard != null) {
            keys = mKeyBoard.getKeys();
        }
        if (keys != null) {
            for (Keyboard.Key key : keys) {
                //可以自定义自己的绘制（例如某个按钮绘制背景图片和文字，亦或者更改某个按钮颜色等）
                //过滤指定某个键自定义绘制
                if (key.codes[0] == -8) {//纯文字
                    //绘制后，原来xml中的keyLabel以及keyIcon会被覆盖,如需显示文字
                    //需要自己重新绘制，要后绘制文字，否则文字不显示
                    drawBackground(R.drawable.shape_null, canvas, key);
                    drawTextOrIcon(canvas, key, 0xFFCCCCCC);
                } else if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {//完成  FFD5B77B
                    drawBackground(R.drawable.shape_null, canvas, key);
                    drawTextOrIcon(canvas, key, 0xFFD5B77B);
                } else if (key.codes[0] == Keyboard.KEYCODE_DELETE) {//删除
                    drawBackground(R.drawable.shape_null, canvas, key);
                    drawTextOrIcon(canvas, key, 0xFFFFFFFF);
                } else {
                    drawBackground(R.drawable.shape_reg, canvas, key);
                    drawTextOrIcon(canvas, key, 0xFFFFFFFF);
                }
            }
        }

    }

    //绘制背景
    private void drawBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable drawable = getResources().getDrawable(drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            drawable.setState(drawableState);
        }

        drawable.setBounds(new Rect(key.x + padding, key.y + padding, key.x + key.width - padding, key.height + key.y - padding));
        drawable.draw(canvas);
    }


    //绘制文字或图标
    private void drawTextOrIcon(Canvas canvas, Keyboard.Key key, @ColorInt int color) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT);
        if (key.label != null) {
            String label = key.label.toString();
            //为了将字体大小与默认绘制的Label字体大小相同，需要反射获取默认大小。然后在此处设置文字大小
            //还有一种取巧的方法在布局文件keyboardview中设置keyTextSize，labelTextSize
            try {
                Class clazz = Class.forName("android.inputmethodservice.KeyboardView");
                //获取Person类的成员属性信息
                Field field = clazz.getDeclaredField("mLabelTextSize");
                //暴力访问(忽略掉访问修饰符)
                field.setAccessible(true);
                int labelTextSize = (Integer) field.get(this);
                paint.setColor(color);

                paint.setTextSize((float) (labelTextSize * 1.2));

                paint.getTextBounds(label, 0, label.length(), bounds);
                canvas.drawText(label, (key.x + key.width / 2), (key.y + key.height / 2 + bounds.height() / 2), paint);
            } catch (Exception e) {
                int x = key.x;
                int width = key.width;
                int intrinsicWidth = key.icon.getIntrinsicWidth();
                int y = key.y;
                int height = key.height;
                int intrinsicHeight = key.icon.getIntrinsicHeight();
                key.icon.setBounds(new Rect(x + (width - intrinsicWidth) / 2,
                        y + (height - intrinsicHeight) / 2,
                        x + (width - intrinsicWidth) / 2 + intrinsicWidth,
                        y + (height - intrinsicHeight) / 2 + intrinsicHeight));
                key.icon.draw(canvas);
            }
        } else if (key.icon != null) {
            int x = key.x;
            int width = key.width;
            int intrinsicWidth = key.icon.getIntrinsicWidth();
            int y = key.y;
            int height = key.height;
            int intrinsicHeight = key.icon.getIntrinsicHeight();
            key.icon.setBounds(new Rect(x + (width - intrinsicWidth) / 2,
                    y + (height - intrinsicHeight) / 2,
                    x + (width - intrinsicWidth) / 2 + intrinsicWidth,
                    y + (height - intrinsicHeight) / 2 + intrinsicHeight));
            key.icon.draw(canvas);
        }
    }



    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }


}
