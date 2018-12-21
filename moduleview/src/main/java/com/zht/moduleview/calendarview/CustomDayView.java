package com.zht.moduleview.calendarview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zht.moduleview.R;
import com.zht.moduleview.calendarview.component.State;
import com.zht.moduleview.calendarview.interf.IDayRenderer;
import com.zht.moduleview.calendarview.model.CalendarDate;
import com.zht.moduleview.calendarview.view.DayView;


public class CustomDayView extends DayView {

    private final View dateBackground;
    private final View dateDot;
    private TextView dateTv;
    private final CalendarDate today = new CalendarDate();
    private boolean isFirstIn = true;

    /**
     * 构造器
     *
     * @param context        上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv = (TextView) findViewById(R.id.calendar_date);
        dateBackground = (View) findViewById(R.id.calendar_selected_background);
        dateDot = (View) findViewById(R.id.calendar_selected_dot);
    }

    @Override
    public void refreshContent() {
        renderContent(day.getDate(), day.getState());
        super.refreshContent();

    }

    private void renderContent(final CalendarDate date, State state) {
        if (date != null) {
            /**
             * 设置字体的颜色
             */
            //判断是不是当前月份的日期，是变为黑色，不是就是灰色
            if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
                dateTv.setTextColor(Color.parseColor("#A3A3A3"));//灰色
            } else {
                dateTv.setTextColor(Color.parseColor("#4E4E4E")); //黑色
            }

            /**
             * 设置文字
             */
            //设置日期
            dateTv.setText(date.day + "");

            /**
             * 设置背景
             */
//            //是否有标记
//            if (Utils.loadMarkData().containsKey(date.toString())) {
//                dateTv.setTextColor(Color.WHITE);
//                dateBackground.setBackgroundResource(R.drawable.calendar_mark_background);
//            } else {
//                if (!date.equals(today)) {
//                    dateBackground.setBackgroundResource(R.drawable.calendar_normal_background);
//                }else {
//                    dateBackground.setBackgroundResource(R.drawable.calendar_normal_background);
//                }
//            }
            /**
             * 设置标记的小圆点
             */
            //有标记
            if (Utils.loadMarkData().containsKey(date.toString())) {
                if (Utils.isBeforeToday(today, date)) {
                    dateDot.setBackgroundResource(R.drawable.calendar_select_dot_gray);
                } else {
                    dateDot.setBackgroundResource(R.drawable.calendar_select_dot_red);
                }
            } else {
                if (!date.equals(today)) {
                    dateDot.setBackgroundResource(R.drawable.calendar_select_dot_normal);
                } else {
                    dateDot.setBackgroundResource(R.drawable.calendar_select_dot_normal);
                }
            }

            //设置今天的样式：黑字体+灰色背景
            if (date.equals(today)) {
                // dateTv.setText("今");
                dateTv.setTextColor(Color.parseColor("#FC4A5B"));
//                dateBackground.setBackgroundResource(R.drawable.calendar_select_background);
//                dateDot.setBackgroundResource(R.drawable.calendar_select_dot_white);
            }

            //选中判断
            if (state == State.SELECT) {
                if (date.equals(today)) {
                    dateTv.setText("今");
                }
                dateTv.setTextColor(Color.WHITE);
                dateBackground.setBackgroundResource(R.drawable.calendar_select_dot_red);
                if (Utils.loadMarkData().containsKey(date.toString())) {
                    dateDot.setBackgroundResource(R.drawable.calendar_select_dot_white);
                }
            } else {
                dateBackground.setBackgroundResource(R.drawable.calendar_normal_background);
            }

            //选中判断
//            if (state == State.SELECT) {
//                dateTv.setTextColor(Color.WHITE);
//                dateBackground.setBackgroundResource(R.drawable.calendar_select_dot_red);
//            }

//            //设置今天的样式：黑字体+灰色背景
//            if (date.equals(today)) {
//                dateTv.setText("今");
//                dateTv.setTextColor(Color.parseColor("#FFFFFF"));
//                dateBackground.setBackgroundResource(R.drawable.calendar_select_dot_red);
//            }
//
            if (isFirstIn && date.equals(today)) {
                isFirstIn = false;
                dateTv.setText("今");
                dateTv.setTextColor(Color.parseColor("#FFFFFF"));
                dateBackground.setBackgroundResource(R.drawable.calendar_select_background);
               // dateDot.setBackgroundResource(R.drawable.calendar_select_dot_white);
            }


        }
    }


    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }
}
