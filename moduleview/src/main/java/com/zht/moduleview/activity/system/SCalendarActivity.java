package com.zht.moduleview.activity.system;

import android.os.Bundle;
import android.widget.CalendarView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.Calendar;

/**
 * Created by ZhangHaitao on 2018/12/21
 */
@Route(path = ARoutePathConstants.View.SCALENDAR_ACTIVITY)
public class SCalendarActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.calendar_system_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        CalendarView  calendar= findViewById(R.id.CalendarView);
        //修改每周以星期几开始
        calendar.setFirstDayOfWeek(Calendar.THURSDAY);


    }



}



