package com.slxk.gpsantu.mvp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeZoneUtil {

    //东八区时间偏移
    private final static int GTM08 =  8 * 3600 * 1000;
    /**
     * GMT转UTC  服务器时区为东八区
     * UTC时间用于服务器
     *
     * @param gmtStr
     * @param formatPatternStr
     * @return
     */
    public static String GetUTCStrFromGTMStr(String gmtStr, String formatPatternStr) {
        if (gmtStr == null || gmtStr.length() == 0) return "";
        if (formatPatternStr == null || formatPatternStr.length() == 0) {
            formatPatternStr = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        // 取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET) - GTM08;
        // 取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);

        SimpleDateFormat format = new SimpleDateFormat(formatPatternStr, Locale.ENGLISH);
        Date dateTo;
        try {
            Date date = format.parse(gmtStr);
            long utcMills = date.getTime() - (zoneOffset + dstOffset);
            dateTo = new Date(utcMills);
        } catch (ParseException e) {
            e.printStackTrace();
            dateTo = new Date();
        }
        SimpleDateFormat formatTo = new SimpleDateFormat(formatPatternStr, Locale.ENGLISH);
        return formatTo.format(dateTo);
    }

    /**
     * UTC转GMT
     * GMT时间用于显示
     *服务器时区为东八区
     * @param utcStr
     * @param formatPatternStr
     * @return
     */
    public static String GetGMTStrFromUTCStr(String utcStr, String formatPatternStr) {
        if (utcStr == null || utcStr.length() == 0) return "";
        if (formatPatternStr == null || formatPatternStr.length() == 0) {
            formatPatternStr = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        // 取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET) - GTM08;
        // 取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);

        SimpleDateFormat format = new SimpleDateFormat(formatPatternStr, Locale.ENGLISH);
        Date dateTo;
        try {
            Date date = format.parse(utcStr);
            long gmtMills = date.getTime() + (zoneOffset + dstOffset);
            dateTo = new Date(gmtMills);
        } catch (ParseException e) {
            e.printStackTrace();
            dateTo = new Date();
        }
        SimpleDateFormat formatTo = new SimpleDateFormat(formatPatternStr, Locale.ENGLISH);
        return formatTo.format(dateTo);
    }

}
