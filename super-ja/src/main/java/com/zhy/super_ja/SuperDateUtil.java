package com.zhy.super_ja;


import java.util.Calendar;

//时间工具类
public class SuperDateUtil {
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
}
