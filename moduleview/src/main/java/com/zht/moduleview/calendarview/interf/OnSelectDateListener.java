package com.zht.moduleview.calendarview.interf;


import com.zht.moduleview.calendarview.model.CalendarDate;

public interface OnSelectDateListener {
    void onSelectDate(CalendarDate date);

    void onSelectOtherMonth(int offset);//点击其它月份日期
}
