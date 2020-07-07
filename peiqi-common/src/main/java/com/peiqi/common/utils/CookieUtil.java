package com.peiqi.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie操作工具类
 * 
 * @author STILL
 *
 */
public class CookieUtil {

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param cookieName  cookie名字
	 * @param cookieValue cookie值
	 * @param path        指定cookie适用的路径
	 * @param domain      设置cookie适用的域名
	 * @param maxAge      以秒计算，设置Cookie过期时间
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String path,
			String domain, int maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath(path);
		cookie.setDomain(domain);
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 将cookie封装到map中
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = null;
		cookieMap = (Map<String, Cookie>) request.getAttribute("cookieMap");
		if (cookieMap == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				cookieMap = new HashMap<String, Cookie>();
				for (Cookie cookie : cookies) {
					cookieMap.put(cookie.getName(), cookie);
				}
				request.setAttribute("cookieMap", cookieMap);
			}
		}

		return cookieMap;
	}

	/**
	 * 根据名字获取指定的cookie
	 * 
	 * @param request
	 * @param cookieName 要获取的cookie的名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
		Map<String, Cookie> cookieMap = getCookieMap(request);
		Cookie cookie = null;
		if (cookieMap != null && cookieMap.containsKey(cookieName)) {
			cookie = cookieMap.get(cookieName);
		}

		return cookie;
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Map<String, Cookie> cookieMap = getCookieMap(request);
		if (cookieMap != null) {
			Cookie cookie = cookieMap.get(cookieName);
			if (cookie != null) {
				cookie.setValue(null);
				cookie.setMaxAge(0);// 立即销毁cookie
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	}

}
