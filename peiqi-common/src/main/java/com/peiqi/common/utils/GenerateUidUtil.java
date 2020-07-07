package com.peiqi.common.utils;

import java.util.UUID;

/**
 * 生成UID工具类
 * 
 * @author STILL
 *
 */
public class GenerateUidUtil {

	/**
	 * 生成UID
	 * 
	 * @return
	 */
	public static String generateUid() {
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString();
		return uid.replaceAll("-", "");
	}

}
