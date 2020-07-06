package com.peiqi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class ParamUtil {
	/**
	 * 从流中获取参数
	 * 
	 * @param req
	 * @return
	 */
	public static JSONObject getParam(HttpServletRequest req) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (VaildUtil.isEmpty(sb)) {
				return new JSONObject();
			}
			return JSONObject.parseObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从流中获取参数
	 * 
	 * @param req
	 * @return
	 */
	public static JSONObject getParam() {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(HttpContextUtils.getHttpServletRequest().getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (VaildUtil.isEmpty(sb)) {
				return new JSONObject();
			}
			return JSONObject.parseObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
