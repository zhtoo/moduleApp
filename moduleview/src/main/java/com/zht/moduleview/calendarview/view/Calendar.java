package com.zht.moduleview.calendarview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zht.moduleview.calendarview.Const;
import com.zht.moduleview.calendarview.Utils;
import com.zht.moduleview.calendarview.component.CalendarAttr;
import com.zht.moduleview.calendarview.component.CalendarRenderer;
import com.zht.moduleview.calendarview.interf.IDayRenderer;
import com.zht.moduleview.calendarview.interf.OnAdapterSelectListener;
import com.zht.moduleview.calendarview.interf.OnSelectDateListener;
import com.zht.moduleview.calendarview.model.CalendarDate;

public class Calendar extends View {
    /**
     * 日历列数
     */
    private CalendarAttr.CalendayType calendarType;
    private int                       cellHeight; // 单元格高度
    private int                       cellWidth; // 单元格宽度

    private OnSelectDateListener onSelectDateListener;    // 单元格点击回调事件
    private Context              context;
    private CalendarAttr         calendarAttr;
    private CalendarRenderer renderer;

    private OnAdapterSelectListener onAdapterSelectListener;
    private float touchSlop;

    public Calendar(Context context, OnSelectDateListener onSelectDateListener) {
        super(context);
        this.onSelectDateListener = onSelectDateListener;
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        touchSlop = Utils.getTouchSlop(context);
        initAttrAndRenderer();
    }

    private void initAttrAndRenderer() {
        calendarAttr = new CalendarAttr();
        calendarAttr.setWeekArrayType(CalendarAttr.WeekArrayType.Monday);
        calendarAttr.setCalendarType(CalendarAttr.CalendayType.MONTH);
        renderer = new CalendarRenderer(this, calendarAttr, context);
        renderer.setOnSelectDateListener(onSelectDateListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        cellHeight = h / Const.TOTAL_ROW;
        cellWidth = w / Const.TOTAL_COL;
        calendarAttr.setCellHeight(cellHeight);
        calendarAttr.setCellWidth(cellWidth);
        renderer.setAttr(calendarAttr);
    }

    private float posX = 0;
    private float posY = 0;

    /*
     * 触摸事件为了确定点击的位置日期
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                posX = event.getX();
                posY = event.getY();
                Log.v("aaa", "pox===" + posX);
                Log.v("aaa", "posY===" + posY);
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - posX;
                float disY = event.getY() - posY;
                /**
                 * row  列
                 * col  行
                 */
//                int col;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int row = (int) (posX / cellWidth);
                    int   col = (int) ((posY)/ cellHeight);


//                    if (posY <= Utils.dpi2px(ActivityUtils.peek(),150) && posY > 0){
//                         col = 0;
//                    }else if (posY <= Utils.dpi2px(ActivityUtils.peek(),300) && posY > Utils.dpi2px(ActivityUtils.peek(),150)){
//                         col = 1;
//                    }else if (posY <= Utils.dpi2px(ActivityUtils.peek(),450) && posY > Utils.dpi2px(ActivityUtils.peek(),300)){
//                         col = 2;
//                    }else if (posY <= Utils.dpi2px(ActivityUtils.peek(),700) && posY > Utils.dpi2px(ActivityUtils.peek(),450)){
//                         col = 3;
//                    }else if (posY <= Utils.dpi2px(ActivityUtils.peek(),850) && posY > Utils.dpi2px(ActivityUtils.peek(),700)){
//                         col = 4;
//                    } else  {
//                         col = 5;
//                    }
                    onAdapterSelectListener.cancelSelectState();
                    renderer.onClickDate(row, col);
                    onAdapterSelectListener.updateSelectState();
                    invalidate();
                }
                break;
        }
        return true;
    }

    public CalendarAttr.CalendayType getCalendarType() {
        return calendarAttr.getCalendarType();
    }

    public void switchCalendarType(CalendarAttr.CalendayType calendarType) {
        calendarAttr.setCalendarType(calendarType);
        renderer.setAttr(calendarAttr);
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void resetSelectedRowIndex() {
        renderer.resetSelectedRowIndex();
    }

    public int getSelectedRowIndex() {
        return renderer.getSelectedRowIndex();
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        renderer.setSelectedRowIndex(selectedRowIndex);
    }

    public void setOnAdapterSelectListener(OnAdapterSelectListener onAdapterSelectListener) {
        this.onAdapterSelectListener = onAdapterSelectListener;
    }

    public void showDate(CalendarDate current) {
        renderer.showDate(current);
    }

    public void updateWeek(int rowCount) {
        renderer.updateWeek(rowCount);
        invalidate();
    }

    public void update() {
        renderer.update();
    }

    public void cancelSelectState() {
        renderer.cancelSelectState();
    }

    public CalendarDate getSeedDate() {
        return renderer.getSeedDate();
    }

    public void setDayRenderer(IDayRenderer dayRenderer) {
        renderer.setDayRenderer(dayRenderer);
    }
}