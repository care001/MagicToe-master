package com.crow.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    private static String year = "2018-";

    private static String miao = ":00";

    /**
     * 根据字符串获取时间
     * 01-23:20:30
     * @return
     */
    public static Date getDateFromString(String date) {
        date = year + date + miao;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date out = null;
        try {
            out = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }
    /**
     * 根据最近一天的时间
     * 01-23:20:30
     * @return
     */
    public static Map<String, Date> getNearOneDay() {
        Date end = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);
        Date start = c.getTime();
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("start", start);
        dateMap.put("end", end);
        return dateMap;
    }

    /**
     * 获取昨天某点的时间
     * 01-23:20:30
     * @return
     */
    public static Date getOneTime() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);
        Date upDay = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String one = formatter.format(upDay);
        one = one + " 18:00:00";
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date out = null;
        try {
            out = formatter2.parse(one);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }


    /**
     * 获取昨天某点的时间
     * 01-23:20:30
     * @return
     */
    public static List<String> getAllWant(String week, int last) {
        List<String> out = new ArrayList<>();
        for (int i=1; i<=last; i++){
            String oneOut = String.valueOf(1000+i);
            out.add(week+oneOut.substring(1, oneOut.length()));
        }
        return out;
    }

    public static void main(String[] args) {


    }

}
