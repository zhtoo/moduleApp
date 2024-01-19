package com.zht.moduleview.activity.customise;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;
import com.zht.moduleview.calendarview.CustomDayView;
import com.zht.moduleview.calendarview.component.CalendarAttr;
import com.zht.moduleview.calendarview.component.CalendarViewAdapter;
import com.zht.moduleview.calendarview.interf.OnSelectDateListener;
import com.zht.moduleview.calendarview.model.CalendarDate;
import com.zht.moduleview.calendarview.view.Calendar;
import com.zht.moduleview.calendarview.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZhangHaitao on 2018/12/21
 */
@Route(path = ARoutePathConstants.View.CALENDAR_ACTIVITY)
public class CalendarActivity extends BaseActivity {

    private CalendarViewAdapter calendarAdapter;
    private MonthPager monthPager;
    private TextView nextMonthBtn;
    private TextView lastMonthBtn;
    private OnSelectDateListener onSelectDateListener;
    private CalendarDate currentDate;
    private TextView showView;
    private ArrayList<Calendar> currentCalendars;

    @Override
    protected int getLayoutId() {
        return R.layout.calendar_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        monthPager = (MonthPager) findViewById(R.id.calendar_view);
        nextMonthBtn = (TextView) findViewById(R.id.next_month);
        lastMonthBtn = (TextView) findViewById(R.id.last_month);
        showView = (TextView) findViewById(R.id.show_view);

        currentDate = new CalendarDate();
        showView.setText(currentDate.getMonth() + "月份课表");

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });

        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                //refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };

        CustomDayView customDayView = new CustomDayView(mContext, R.layout.calendar_custom_day);
        calendarAdapter = new CalendarViewAdapter(mContext, onSelectDateListener, CalendarAttr.CalendayType.MONTH, customDayView);
        calendarAdapter.setWeekArrayType(1);
        initMarkData();
        initMonthPager();

    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     *
     * @return void
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2018-12-6", "1");
        markData.put("2018-12-19", "0");
        markData.put("2018-12-29", "1");
        markData.put("2018-12-20", "0");
        markData.put("2018-12-21", "0");
        markData.put("2019-1-20", "1");
        markData.put("2019-2-18", "1");
        calendarAdapter.setMarkData(markData);
    }


    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);

        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                currentCalendars = calendarAdapter.getPagers();

                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    showView.setText(currentDate.getMonth() + "月份课表");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}



