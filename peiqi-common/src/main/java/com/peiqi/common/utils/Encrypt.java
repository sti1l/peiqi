package com.peiqi.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author STILL
 *
 */
public class Encrypt {

	private static String MD5 = "MD5";
	private static String SHA = "SHA-1";

	/**
	 * MD5加密
	 * 
	 * @param string
	 * @return
	 */
	public static String MD5(String string) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] older = string.getBytes();
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			md5.update(older);
			byte[] newer = md5.digest();
			int j = newer.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = newer[i];
				chars[k++] = hexDigits[b >>> 4 & 0xf];
				chars[k++] = hexDigits[b & 0xf];
			}
			return new String(chars);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SHA-1加密
	 * 
	 * @param info
	 * @return
	 */
	public static String SHA(String info) {
		try {
			MessageDigest md = MessageDigest.getInstance(SHA);
			md.update(info.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Md5一次加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {

			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * 密码加密(三次加密，测试结果，等同于DotNet加密)
	 * 
	 * @param password
	 * @param salt     密码盐
	 * @return 加密过的密码
	 */
	public static String encryptUserPassword(String password, String salt) {
		// 加密结果
		String result = null;
		try {
			// first
			result = getMD5Str(password).toUpperCase();
			// salt必须为20位
			if (salt != null && salt.length() == 20) {

				result = salt.substring(6) + result + salt.substring(6, 16);
				// second
				result = getMD5Str(result).toUpperCase();

				result = result + salt;
				// third
				result = getMD5Str(result).toUpperCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
