package com.zht.moduleview.calendarview.interf;

import android.graphics.Canvas;

import com.zht.moduleview.calendarview.view.Day;

public interface IDayRenderer {

    void refreshContent();

    void drawDay(Canvas canvas, Day day);

    IDayRenderer copy();

}
