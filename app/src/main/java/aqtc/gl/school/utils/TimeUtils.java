package aqtc.gl.school.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author gl
 * @date 2018/6/12
 * @desc 时间工具类
 */
public class TimeUtils {
    /**
     * 获取中国时间
     *
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        return new SimpleDateFormat(format, Locale.CHINA)
                .format(new Date());
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    public static String format(String format, String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeInMillis(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 返回日期，不包含时分秒
     *
     * @param time
     * @return
     */
    public static String getTimeDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(time));
    }

    /***
     * 获取时间差值
     */
    public static long getBetween(String begin, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = format.parse(begin);
            Date date2 = format.parse(end);
            long diff = date1.getTime() - date2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return minutes;
        } catch (Exception e) {
            return 0;
        }
    }

    /***
     * 获取时间差值
     */
    public static String getWechatTimeBetween(String begin, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String between = end;
        try {
            Date date1 = format.parse(begin);
            Date date2 = format.parse(end);
            long minutes = date1.getTime() - date2.getTime();
            minutes = minutes / (1000 * 60);
            if (minutes < 1) {
                between = "刚刚";
            } else if (minutes >= 1 && minutes < 60) {
                between = minutes + "分钟前";
            } else if (minutes >= 60 && minutes < 60 * 24) {
                between = minutes / 60 + "小时前";
            } else if (minutes >= 60 * 24 && minutes < 60 * 24 * 30) {
                between = minutes / (60 * 24) + "天前";
            } else if (minutes >= 60 * 24 * 30) {
                format.applyPattern("MM月dd日");
                between = format.format(date2);
            } else {
                format.applyPattern("MM月dd日");
                between = format.format(date2);
            }
            return between;
        } catch (Exception e) {
            return between;
        }
    }

    /****
     * 日期比较大小
     */
    public static int compareDate(String DATE1, String DATE2, DateFormat df) {
        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
