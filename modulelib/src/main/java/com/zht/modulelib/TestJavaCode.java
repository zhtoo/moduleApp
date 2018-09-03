package com.zht.modulelib;

import android.support.annotation.NonNull;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TestJavaCode {

    public static void main(String[] args) {
        long nowTime = System.currentTimeMillis();//当前时间毫秒数
        long zero = nowTime / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();//今天0点0分0秒的毫秒数
        long todayLast = zero + 24 * 60 * 60 * 1000;//明天0点0分0秒的毫秒数
        long tomorrowLast = zero + 24 * 60 * 60 * 1000 * 2;//明天24点0分0秒的毫秒数
        long afterTomorrowLast = zero + 24 * 60 * 60 * 1000 * 3;//后天24点0分0秒的毫秒数

        long begintime = zero - 3600 * 1000 * 24;

        long dTime = 3600 * 1000 * 25;
        long endtime = begintime + dTime;

        nowTime = System.currentTimeMillis();
        formatCourseTime(begintime, endtime, 2);
        endtime = System.currentTimeMillis();
        System.out.println("耗时" + (endtime - nowTime) + "ms");
    }


    private static String formatCourseTime(long begintime, long endtime, int inClassStatus) {
        String time;
        String beginDate = longToDate("yyyy.MM.dd", begintime);
        String endDate = longToDate("yyyy.MM.dd", endtime);
        if (beginDate.equals(endDate)) {//开始和结束在同一天
            time = calculatedDate(begintime, true);
            time += " " + longToDate("HH:mm", begintime) + "至"
                    + longToDate("HH:mm", endtime);
        } else {//开始和结束不在同一天
            time = calculatedDate(begintime, true);
            time += longToDate("HH:mm", begintime) + "至"
                    + calculatedDate(endtime, false)
                    + longToDate("HH:mm", endtime);
        }

        int i = time.indexOf(":");
        String oldChar;
        if (i > 0) {
            oldChar = time.substring(0, i);
        } else {
            oldChar = "******";
        }
        //（1：直播中,0未开始,2已结束）
        switch (inClassStatus) {
            case 0:
                break;
            case 1:
                time = time.replace(oldChar, "正在直播");
                break;
            case 2:
                time = time.replace(oldChar, "已结束");
                break;
        }
        return time;
    }


    private static String calculatedDate(long begintime, boolean hasPrefix) {
        //获取当前时间毫秒数
        long nowTime = System.currentTimeMillis();
        //获取今天零点时间毫秒数
        long zero = nowTime / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();//今天0点0分0秒的毫秒数
        //获取今天结束时间毫秒数
        long todayLast = zero + 24 * 60 * 60 * 1000;//明天0点0分0秒的毫秒数
        //获取明天结束时间毫秒数
        long tomorrowLast = zero + 24 * 60 * 60 * 1000 * 2;//明天24点0分0秒的毫秒数
        //获取后天结束时间毫秒数
        long afterTomorrowLast = zero + 24 * 60 * 60 * 1000 * 3;//后天24点0分0秒的毫秒数
        String time;
        if (begintime < zero) {//正在直播
            time = hasPrefix ? "下节课:" : "";
            time += longToDate("yyyy.MM.dd", begintime) + " ";
        } else if (begintime < nowTime) {//正在直播
            time = hasPrefix ? "正在直播:今天" : "今天";
        } else if (begintime < todayLast) {//开始时间在今天
            time = hasPrefix ? "即将开始:今天" : "今天";
        } else if (begintime < tomorrowLast) {//开始时间在明天
            time = hasPrefix ? "下节课:明天" : "明天";
        } else if (begintime < afterTomorrowLast) {//开始时间在后天
            time = hasPrefix ? "下节课:后天" : "后天";
        } else {
            time = hasPrefix ? "下节课:" : "";
            time += longToDate("yyyy.MM.dd", begintime) + " ";
        }
        return time;
    }

    /**
     * @param dateFormat 格式
     * @param millSec    时间
     * @return
     */
    public static String longToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


}
