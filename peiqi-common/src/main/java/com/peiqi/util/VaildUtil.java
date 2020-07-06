package com.peiqi.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数据验证工具类
 * 
 * @author STILL
 *
 */
public class VaildUtil {

	/**
	 * 空字符串验证
	 * 
	 * @param str
	 * @return 空字符串返回true,否则返回false
	 */
	public static boolean isEmpty(Object data) {
		boolean isEmpty = true;
		if (data == null || (data instanceof String && "".equals(((String) data).replaceAll("\\s", "")))) {
			isEmpty = true;
		} else {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * 非空字符串验证
	 * 
	 * @param str
	 * @return 非空字符串返回true,否则返回false
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null && !str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串长度验证,不允许为空
	 * 
	 * @param address
	 * @param len
	 * @return
	 */
	public static boolean checkStrLength(String str, int len) {
		if (str == null || "".equals(str.trim())) {
			return false;
		} else if (str.length() > len) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 字符串长度验证,允许为空
	 * 
	 * @param str 字符串
	 * @param len 最大长度
	 * @return
	 */
	public static boolean checkStrLen(String str, int len) {
		if (str != null && str.length() > len) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证手机号码的有效性
	 * 
	 * @param mob
	 * @return
	 */
	public static boolean isValMobile(String mob) {
		boolean b = false;
		if (mob != null && !mob.isEmpty() && mob.matches("^1\\d{10}$")) {
			b = true;
		}
		return b;
	}

	/**
	 * 验证电话号码的有效性
	 * 
	 * @param tel
	 * @return
	 */
	public static boolean isValTel(String tel) {
		boolean b = false;
		if (tel != null && !tel.isEmpty() && tel.matches("^\\d{3,4}-?\\d{7,8}$")) {
			b = true;
		}
		return b;
	}

	/**
	 * 大数值格式化,不需要分隔符
	 * 
	 * @param num
	 * @return
	 */
	public static String formatNumber(double num) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(false);
		return format.format(num);
	}

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value     double 精度描述（0.00表示精确到小数点后两位）
	 * @param precision String #.00 表示两位小数 #.0000四位小数 以此类推...
	 * @return double
	 */
	public static double setPrecision(double value, String precision) {
		return Double.parseDouble(new DecimalFormat(precision).format(value));
	}

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value double 精度描述（0.00表示精确到小数点后两位）
	 * @return double
	 */
	public static double setPrecisionTwo(double value) {
		return Double.parseDouble(new DecimalFormat("#.00").format(value));
	}

}
