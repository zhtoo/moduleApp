package com.zht.moduleview.calendarview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zht.moduleview.calendarview.interf.IDayRenderer;

public abstract class DayView extends RelativeLayout implements IDayRenderer {

    protected Day day;
    protected Context context;
    protected int layoutResource;

    /**
     * 构造器 传入资源文件创建DayView
     *
     * @param layoutResource 资源文件
     * @param context 上下文
     */
    public DayView(Context context, int layoutResource) {
        super(context);
        setupLayoutResource(layoutResource);
        this.context = context;
        this.layoutResource = layoutResource;
    }

    /**
     * 为自定义的DayView设置资源文件
     *
     * @param layoutResource 资源文件
     * @return CalendarDate 修改后的日期
     */
    private void setupLayoutResource(int layoutResource) {
        View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        inflated.setLayoutParams(layoutParams);
        inflated.measure(layoutParams.width,layoutParams.height);
//        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        inflated.measure(width,height);
        inflated.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void refreshContent() {
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void drawDay(Canvas canvas, Day day) {
        this.day = day;
        refreshContent();
        int saveId = canvas.save();
        canvas.translate(getTranslateX(canvas, day),
                day.getPosRow() * getMeasuredHeight());
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    private int getTranslateX(Canvas canvas, Day day) {
        int dx;
        int canvasWidth = canvas.getWidth() / 7;
        int viewWidth = getMeasuredWidth();
        int moveX = (canvasWidth - viewWidth) / 2;
        dx = day.getPosCol() * canvasWidth + moveX;
        return dx;
    }
}