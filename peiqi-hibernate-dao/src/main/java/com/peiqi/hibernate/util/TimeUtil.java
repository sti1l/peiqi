package com.peiqi.hibernate.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * 
 * @author STILL
 *
 */
public class TimeUtil {
	
	//
	//=========================================格式化常量=======================================
	//
	/**
	 * 年-月-日
	 */
	public static final String FORMAT_DAY = "yyyy-MM-dd";
	
	/**
	 * 年月日
	 */
	public static final String FORMATDAY = "yyyyMMdd";
	
	/**
	 * 年-月-日 时:分:秒
	 */
	public static final String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 年月日 时分秒
	 */
	public static final String FORMATSECOND = "yyyyMMddHHmmss";
	
	/**
	 * 年-月-日 时:分:秒.毫秒
	 */
	public static final String FORMAT_MSEL = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/**
	 * 年月日时分秒毫秒
	 */
	public static final String FORMATMSEL = "yyyyMMddHHmmssSSS";
	
	

	/**
	 * 获取当前时间精确到天的日期字符串yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDateStrToDay() {
		Date date = new Date();
		return formatDateToDay(date);
	}

	/**
	 * 获取当前时间精确到天的日期字符串yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDateStrToDayNoSeparator() {
		Date date = new Date();
		return formatDateToDayNoSeparator(date);
	}

	/**
	 * 获取当前时间精确到秒的字符串yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateStrToSec() {
		Date date = new Date();
		return formatDateToSecond(date);
	}

	/**
	 * 获取当前时间精确到秒的字符串yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDateStrToSecNoSeparator() {
		Date date = new Date();
		return formatDateToSecNoSeparator(date);
	}

	/**
	 * 获取当前时间精确到毫秒的字符串yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String getDateStrToMill() {
		Date date = new Date();
		return formatDateToMill(date);
	}

	/**
	 * 获取当前时间精确到毫秒的字符串yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getDateStrToMillNoSeparator() {
		Date date = new Date();
		return formatDateToMillNoSeparator(date);
	}

	/**
	 * 日期格式化 .Date -> yyyy-MM-dd
	 * 
	 * @param date 日期
	 * @return yyyy-MM-dd格式的日期字符串
	 */
	public static String formatDateToDay(Date date) {
		DateFormat format = new SimpleDateFormat(FORMAT_DAY);
		return format.format(date);
	}

	/**
	 * 日期格式化 .Date -> yyyyMMdd
	 * 
	 * @param date 日期
	 * @return yyyyMMdd格式的日期字符串
	 */
	public static String formatDateToDayNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat(FORMATDAY);
		return format.format(date);
	}

	/**
	 * 日期格式化 .Date -> yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 日期
	 * @return yyyy-MM-dd HH:mm:ss格式的日期字符串
	 */
	public static String formatDateToSecond(Date date) {
		DateFormat format = new SimpleDateFormat(FORMAT_SECOND);
		return format.format(date);
	}

	/**
	 * 格式化日期 .Date -> yyyyMMddHHmmss
	 * 
	 * @param date 日期
	 * @return yyyyMMddHHmmss格式的日期字符串
	 */
	public static String formatDateToSecNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat(FORMATSECOND);
		return format.format(date);
	}

	/**
	 * 格式化日期 .Date-> format
	 * 
	 * @param date 日期
	 * @return format格式的日期字符串
	 */
	public static String formatDate(Date date, String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(date);
	}

	/**
	 * 格式化日期 .Date-> yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param date 日期
	 * @return yyyy-MM-dd HH:mm:ss.SSS格式的日期字符串
	 */
	public static String formatDateToMill(Date date) {
		DateFormat format = new SimpleDateFormat(FORMAT_MSEL);
		return format.format(date);
	}

	/**
	 * 格式化日期 .Date-> yyyyMMddHHmmssSSS
	 * 
	 * @param date 日期
	 * @return yyyyMMddHHmmssSSS格式的日期字符串
	 */
	public static String formatDateToMillNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat(FORMATMSEL);
		return format.format(date);
	}

	/**
	 * 比较传参日期和当前日期
	 * 
	 * @param date 日期
	 * @return -1: 当前日期小于传参日期, 当前日期0等于传参日期, 当前日期1大于传参日期
	 */
	public static Integer compareDateToCurdateByday(Date date) {

		// 对传参日期进行格式化，精确到天
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// 构建当前日期，并进行格式化，精确到天
		Calendar curCalendar = GregorianCalendar.getInstance();
		curCalendar.set(Calendar.HOUR_OF_DAY, 0);
		curCalendar.set(Calendar.MINUTE, 0);
		curCalendar.set(Calendar.SECOND, 0);
		curCalendar.set(Calendar.MILLISECOND, 0);

		// 比较传参日期和当前日期
		Long valueL = calendar.getTime().getTime() - curCalendar.getTime().getTime();
		if (valueL == 0) {
			return 0;
		}
		if (valueL > 0) {
			return 1;
		}
		return -1;
	}

	/**
	 * 取当前时间戳
	 * 
	 * @return
	 */
	public static Timestamp getDateToTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 日期字符串转Date
	 * 
	 * @param sdate  日期字符串
	 * @param format 日期格式
	 * @return date Date
	 * @throws ParseException
	 */
	public static Date parseDate(String sdate, String format) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.parse(sdate);
	}

	/**
	 * Date转日期字符串
	 * 
	 * @param date   Date
	 * @param farmat 日期格式
	 * @return
	 */
	public static String formatDateToDay(Date date, String farmat) {
		DateFormat format = new SimpleDateFormat(farmat);
		return format.format(date);
	}

	/**
	 * 时间戳转日期字符串
	 * 
	 * @param time   时间戳
	 * @param farmat 日期格式
	 * @return
	 */
	public static String formatLongTimeToDate(Long time, String farmat) {
		SimpleDateFormat formatter = new SimpleDateFormat(farmat);
		Date date = new Date(time);
		return formatter.format(date);
	}

}
