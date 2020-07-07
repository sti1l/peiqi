package com.peiqi.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 文件读取工具类
 * 
 * @author STILL
 *
 */
public class ConfigUtil {
	private static Properties table = new Properties();
	static {
		try {
			table.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据key获取int类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(table.getProperty(key));
	}

	/**
	 * 根据key获取String类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return table.getProperty(key);
	}

	/**
	 * 根据key获取double类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static double getDouble(String key) {
		return Double.parseDouble(table.getProperty(key));
	}

}
