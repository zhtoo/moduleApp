package com.zht.moduleview.qucikindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2019/4/18
 */
public class QuickIndexBar extends View {
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z","#"};

    private int COLOR_DEFAULT = Color.DKGRAY;
    private int COLOR_PRESSED = Color.CYAN;

    private Paint paint;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化的方法
     */
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//开启抗锯齿
        paint.setColor(COLOR_DEFAULT);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textSize));
        //设置文字被绘制的起点为底边的中心
        paint.setTextAlign(Paint.Align.CENTER);
    }

    private float cellHeight;//格子高度

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //遍历绘制26个字母
        for (int i = 0; i < indexArr.length; i++) {
            float x = getMeasuredWidth() / 2;
            //y坐标：格子高度一半 + 文字高度一半 + i*格子高度
            float y = cellHeight / 2 + getTextHeight(indexArr[i]) / 2 + i * cellHeight;

            //当重回字母的时候，可以判断当前正在绘制的和按下的是否是同一个，
            paint.setColor(i==touchIndex?COLOR_PRESSED : COLOR_DEFAULT);

            canvas.drawText(indexArr[i], x, y, paint);
        }
    }

    private int touchIndex = -1;//触摸字母的索引

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算触摸点对应字母索引的算法：y坐标除以格子高度得到的就是字母的索引
                int index = (int) (event.getY() / cellHeight);
                if (index != touchIndex) {
                    //说明字母变化了

                    //对索引进行安全性的检查
                    if(index>=0 && index<indexArr.length){
                        String word = indexArr[index];
                        //回调接口
                        if(listener!=null){
                            listener.onLetterChange(word);
                        }
                    }

                    touchIndex = index;
                }
                break;
            case MotionEvent.ACTION_UP:
                //重置touchIndex
                touchIndex = -1;
                break;
        }

        //重新绘制，改变字母的颜色
        invalidate();

        return true;
    }

    /**
     * 获取文字的高度
     *
     * @param s
     * @return
     */
    private int getTextHeight(String s) {
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);//代码执行完毕，bounds就有值了
        return bounds.height();
    }

    private OnLetterChangeListener listener;

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.listener = listener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }
}