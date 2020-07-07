package com.peiqi.common.utils;

import java.math.BigDecimal;


public class NumFormatUtil {
	
	/**
	 * 判断是否为数值类型的对象，是返回true,否返回false
	 * @param numObj
	 * @return
	 */
	public static boolean isNum(Object numObj) {
		return numObj instanceof Double || numObj instanceof Float || numObj instanceof Integer || numObj instanceof  Short || numObj instanceof Byte ;
	}
	
	/**
	 * 获取Double类型的值，如果传值无法转换成数值类型，则返回null
	 * @param numObj
	 * @return
	 */
	public static Double getDoubleValue(Object numObj) {
		Double num = null;
		try {
			if (isNum(numObj) || numObj instanceof String) {
				BigDecimal bd = new BigDecimal(String.valueOf(numObj));
				num = bd.doubleValue();
			}
		} catch (Exception e) {
			
		}
		
		return num;
	}
	
	/**
	 * 获取Float类型的值，如果传值无法转换成数值类型，则返回null
	 * @param numObj
	 * @return
	 */
	public static Float getFloatValue(Object numObj) {
		Float num = null;
		try {
			if (isNum(numObj) || numObj instanceof String) {
				BigDecimal bd = new BigDecimal(String.valueOf(numObj));
				num = bd.floatValue();
			}
		} catch (Exception e) {
			
		}
		
		return num;
	}
	
	/**
	 * 获取Integer类型的值，如果传值无法转换成数值类型，则返回null
	 * @param numObj
	 * @return
	 */
	public static Integer getIntegerValue(Object numObj) {
		Integer num = null;
		try {
			if (isNum(numObj) || numObj instanceof String) {
				BigDecimal bd = new BigDecimal(String.valueOf(numObj));
				num = bd.intValue();
			}
		} catch (Exception e) {

		}
		
		return num;
	}
	
	/**
	 * 获取Short类型的值，如果传值无法转换成数值类型，则返回null
	 * @param numObj
	 * @return
	 */
	public static Short getShortValue(Object numObj) {
		Short num = null;
		try {
			if (isNum(numObj) || numObj instanceof String) {
				BigDecimal bd = new BigDecimal(String.valueOf(numObj));
				num = bd.shortValue();
			}
		} catch (Exception e) {
			
		}
		
		return num;
	}
	
	/**
	 * 获取Byte类型的值，如果传值无法转换成数值类型，则返回null
	 * @param numObj
	 * @return
	 */
	public static Byte getByteValue(Object numObj) {
		Byte num = null;
		try {
			if (isNum(numObj) || numObj instanceof String) {
				BigDecimal bd = new BigDecimal(String.valueOf(numObj));
				num = bd.byteValue();
			}
		} catch (Exception e) {

		}
		
		return num;
	}
	
}
