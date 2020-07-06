package com.peiqi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormats {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// 获得当前日期与本周一相差的天数
    @SuppressWarnings("unused")
	private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    } 
    /**
     * 获得本周的第一天，周一
     * 
     * @return
     */
    public static String getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        String lastday = null;
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            lastday = dateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastday;
    }

    /**
     * 获得本周的最后一天，周日
     * 
     * @return
     */
    public static String getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        String lastday = null;
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            lastday = dateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastday;
    }
    // 获得当前月--开始日期
    public static String getMinMonthDate() {
         Calendar calendar = Calendar.getInstance();   
          try {
             calendar.setTime(new Date());
             calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); 
             return dateFormat.format(calendar.getTime());
           } catch (Exception e) {
        	   e.printStackTrace();
          }
        return null;
    }
    // 获得当前月--结束日期
    public static String getMaxMonthDate(){   
         Calendar calendar = Calendar.getInstance();   
         try {
                calendar.setTime(new Date());
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                return dateFormat.format(calendar.getTime());
         }  catch (Exception e) {
                e.printStackTrace();
          }
        return null;
}
    /**
     * 当前季度的开始时间，即2012-01-1
     * 
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = dateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31
     * 
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = dateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前年份
     */
	@SuppressWarnings("static-access")
	public static Integer getYears(){
    	Calendar c = Calendar.getInstance();
    	return c.get(c.YEAR);
    }
	/*public static void main(String[] args){
		System.out.println(getYears());
	}*/
	
}
