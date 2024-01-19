package com.zht.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Date 2024/1/16 18:13
 * @Author zhanghaitao
 * @Description
 * d   月中的某一天。一位数的日期没有前导零。
 * dd  月中的某一天。一位数的日期有一个前导零。
 * ddd 周中某天的缩写名称，在 AbbreviatedDayNames 中定义。
 * dddd 周中某天的完整名称，在 DayNames 中定义。
 * M  月份数字。一位数的月份没有前导零。
 * MM  月份数字。一位数的月份有一个前导零。
 * MMM 月份的缩写名称，在 AbbreviatedMonthNames 中定义。
 * MMMM 月份的完整名称，在 MonthNames 中定义。
 * У   不包含纪元的年份。不具有前导零。
 * YY  不包含纪元的年份。具有前导零。
 * YYYY  包括纪元的四位数的年份。
 * gg    时期或纪元。
 * h   12 小时制的小时。一位数的小时数没有前导零。
 * hh  12 小时制的小时。一位数的小时数有前导零。
 * H   24 小时制的小时。一位数的小时数没有前导零。
 * HH   24 小时制的小时。一位数的小时数有前导零。
 * m   分钟。一位数的分钟数没有前导零。
 * mm  分钟。一位数的分钟数有一个前导零。
 * S  秒。一位数的秒数没有前导零。
 * SS  秒。一位数的秒数有一个前导零。
 * f  秒的小数精度为一位。其余数字被截断。
 */
public class DateUtil {

    public static final long UTC_OFFSET = TimeZone.getDefault().getRawOffset();
    public static final long HOUR_SECS = 60 * 60;
    public static final long HALF_HOUR_MILLS = 30 * 60 * 1000;
    public static final long QUARTER_HOUR_MILLS = 15 * 60 * 1000;
    public static final long SECOND_MILLS = 1000;
    public static final long DAY_SECOND_MILLS = 24 * 60 * 60 * 1000;

    /**
     * @param millSecond
     * @return 0 :今天，1：明天，2：後天，-1：昨天，-2：前天
     */
    public static int getDayOffset(long millSecond) {
        long today = (System.currentTimeMillis() + UTC_OFFSET) / DAY_SECOND_MILLS;
        long target = (millSecond + UTC_OFFSET) / DAY_SECOND_MILLS;
        return (int) (target - today);
    }

    public static boolean isBeforeYesterday(long time) {
        return getDayOffset(time) == -2;
    }

    public static boolean isYesterday(long time) {
        return getDayOffset(time) == -1;
    }

    public static boolean isToday(long time) {
        return getDayOffset(time) == 0;
    }

    public static boolean isNextDay(long time) {
        return getDayOffset(time) == 1;
    }

    public static boolean isThirdDay(long time) {
        return getDayOffset(time) == 2;
    }

    /**
     * 根据传入毫秒数返回星期
     *
     * @param millis
     */
    public static String getDayInWeek(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "一";
            case Calendar.TUESDAY:
                return "二";
            case Calendar.WEDNESDAY:
                return "三";
            case Calendar.THURSDAY:
                return "四";
            case Calendar.FRIDAY:
                return "五";
            case Calendar.SATURDAY:
                return "六";
            case Calendar.SUNDAY:
                return "日";
            default:
                break;
        }
        return "";
    }

    public static boolean isThisMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.setTimeInMillis(time);
        int month0 = calendar.get(Calendar.MONTH);
        return month == month0;
    }

    public static boolean isThisYear(long time) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(time);
        int year0 = calendar.get(Calendar.YEAR);
        return year == year0;
    }

    /**
     * 根据年月日判断是否为同一天
     *
     * @param day1 日期1，day2 日期2
     */
    public static boolean isInSameDay(Calendar day1, Calendar day2) {
        if (day1 == null || day2 == null) {
            return false;
        }
        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR) && day1.get(Calendar.MONTH) == day2.get(Calendar.MONTH) && day1.get(Calendar.DAY_OF_MONTH) == day2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isInSameDay(Long dayTimeMillis1, Long dayTimeMillis2) {
        if (dayTimeMillis1 == null || dayTimeMillis2 == null) {
            return false;
        }
        if (dayTimeMillis1 == 0 || dayTimeMillis2 == 0) {
            return false;
        }
        Calendar day1 = Calendar.getInstance();
        day1.setTime(new Date(dayTimeMillis1));
        Calendar day2 = Calendar.getInstance();
        day2.setTime(new Date(dayTimeMillis2));
        return isInSameDay(day1, day2);
    }

    /**
     * 根据传入的日期格式返回对应的格式化字符串
     *
     * @param time
     * @param format
     * @return 格式化后的时间
     */
    public static String formatDateTimeByFormat(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

    /**
     * 根据传入时长返回格式化后的时间, eg  "01:18:18"
     */
    public static String formatTimeWithSecond(long timeSecond) {
        long hour = timeSecond / 3600;
        long minute = (timeSecond % 3600) / 60;
        long second = timeSecond % 60;

        System.currentTimeMillis();
        System.currentTimeMillis();

        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        return strHour + ":" + strMinute + ":" + strSecond;
    }

    public static String formatTimeWithMillis(long timeMillis) {
        return formatTimeWithSecond(timeMillis / 1000);
    }


    /**
     * String类型的date转long
     *
     * @param dateFormat yyyy-MM-dd HH:mm:ss.SSS
     * @param millSec
     * @return
     */
    public static Long dateToLong(String dateFormat, String millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date;
        try {
            date = sdf.parse(millSec);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
