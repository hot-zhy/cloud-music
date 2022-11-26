package com.zhy.util;


import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

//时间工具类
public class SuperDateUtil {
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR=360000L;
    private static final long ONE_DAY=8640000L;
    public static String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

    public static int currentYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 将毫秒转化成分钟和秒
     * @return
     */
    public static String ms2ms(int data) {
        if(data==0){
            return "00:00";
        }
        data/=1000;
        return s2ms(data);
    }
    /**
     * 秒转为分钟
     */
    private static String s2ms(int data){
        int m=data/60;
        int s=data-(m*60);
        return String.format("%02d:%02d",m,s);
    }

    public static String commonFormat(String createAt) {
        DateTime dateTime = new DateTime();
        return commonFormat(dateTime);
    }

    public static String commonFormat(DateTime dateTime) {
        //计算时间差
        long value=new Date().getTime()-dateTime.toDate().getTime();
        if(value<1L*ONE_MINUTE){
            long data=toSeconds(value);
            return String.format("%d秒前", data <= 0 ? 1 : data);
        }else if(value<60*ONE_MINUTE){
            long data = toMinutes(value);
            return String.format("%d分钟前", data);
        }else if (value < 24 * ONE_HOUR){
            long data = toHours(value);
            return String.format("%d小时前", data);
        }else if (value < 30 * ONE_DAY) {
            long data = toDays(value);
            return String.format("%d天前", data);
        }
        return yyyyMMddHHmm(dateTime);
    }
    /**
     * 将DateTime转为yyyy-MM-dd HH:mm
     *
     * @param data
     * @return
     */
    public static String yyyyMMddHHmm(DateTime data) {
        return data.toString(yyyyMMddHHmm);
    }

    /**
     * 转为秒
     *
     * @param date
     * @return
     */
    private static long toSeconds(long date) {
        return date / 1000L;
    }

    /**
     * 转为分钟
     *
     * @param date
     * @return
     */
    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    /**
     * 转为小时
     *
     * @param date
     * @return
     */
    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    /**
     * 转为天
     *
     * @param date
     * @return
     */
    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    /**
     * 转为月
     *
     * @param date
     * @return
     */
    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

}
