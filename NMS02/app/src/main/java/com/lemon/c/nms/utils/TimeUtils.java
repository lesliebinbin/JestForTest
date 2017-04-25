package com.lemon.c.nms.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by C on 2016/9/1.
 */
public class TimeUtils {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    //时间戳转时间
    public static String GetTime(String time){
        String re_StrTime = null;
        float lcc_time = Float.parseFloat(time);
        re_StrTime = sdf.format(new Date((long) lcc_time));
        return re_StrTime;
    }

    public static String getCurrentTime(){
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    //获取多少小时之前的时间
    public static String getHourBefore(int i){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) -i);
        return sdf.format(cal.getTime());

    }

    /**
     * 得到几天前的时间
     */
    public static String getDateBefore(int day){
        Calendar now =Calendar.getInstance();
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return sdf.format(now.getTime());
    }
    /**
     * 得到几天后的时间
     */
    public static Date getDateAfter(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }
}
