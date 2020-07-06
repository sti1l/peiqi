package com.peiqi.util;

public class ExceptionUtil {

	/**
	 * 获取完整堆栈异常信息
	 * 
	 * @param stackTrace
	 * @return
	 */
	public static String getWholeExceptionMsg(StackTraceElement[] stackTrace) {
		StringBuilder msgBuilder = new StringBuilder();
		for (StackTraceElement stackTraceElement : stackTrace) {
			msgBuilder.append(stackTraceElement);
		}
		return msgBuilder.toString();
	}

}
