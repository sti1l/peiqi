package com.peiqi.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取二个地点之间的直线距离工具类 (高德API)
 * 
 * @author STILL
 *
 */
public class DistanceUtil {

	private static final String GD_KEY = "f003fef81a5713df12296e8ed368ca90";

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 距离测量API服务地址
	 * 
	 * @param origins     出发点
	 * @param destination 目的地
	 * @return
	 */
	public static String getDistanceUrl(String origins, String destination) {
		String urlString = "http://restapi.amap.com/v3/distance?type=0&origins=" + origins + "&destination="
				+ destination + "&key=" + GD_KEY;
		return urlString;
	}

	/**
	 * 获取二个地点之间的直线距离
	 * 
	 * @param origins     出发地 （如：“120.539516,31.276413”）
	 * @param destination 目的地 （如：“120.519369,31.261896”）
	 * @return
	 */
	public static String getDistance(String origins, String destination) {
		String jsonString = sendGet(getDistanceUrl(origins, destination));
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String distance = jsonObject.getJSONArray("results").getJSONObject(0).getString("distance");
		return distance;
	}

}
