package com.dower.demo.comm.util.date;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 获取时间序列
 * @author NiuXueJun
 * 2015-8-5 上午9:49:25
 */
public class KeyGenUtil {
	private static AtomicInteger seqid = new AtomicInteger(0);

	/**
	 * 格式化当前时间
	 * @param format yyyyMMddHHmmss
	 * @return String 
	 */
	public static String getCurrDateTimeString(String format) {
		String dateString = "123";
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
			java.util.Date currentTime_1 = new java.util.Date();
			dateString = formatter.format(currentTime_1);
		} catch (Exception e) {
		}
		return dateString;
	}

	/**
	 * 获取时间序列值
	 * @return String  201508051003110000，201508051003110001，201508051003110002
	 */
	public static String genKey() {
		String currDateTimeString = getCurrDateTimeString("yyyyMMddHHmmss");
		int nextNum = seqid.getAndIncrement();
		String str = String.format("%04d", nextNum);
		return currDateTimeString + str;
	}

}
