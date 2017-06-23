package com.dower.demo.comm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 将字符串转成数值封装类型,空字符串对应null.
 * 
 */
public class QueryParamUtils {
	
	/**
	 * 判断字符类型,如果为空则转为null
	 * @param s
	 * @return String
	 */
	public static String toString(String s){
		if ("".equals(s) || s == null) {
			return null;
		}
		
		return s;
	}
	/**
	 * 将String转为Integer,空字符串("")转为null.
	 * @param String
	 * @return Integer
	 */
	public static Integer toInteger(String s) {
		if ("".equals(s) || s == null) {
			return null;
		}

		return Integer.valueOf(s);
	}

	/**
	 * 将String转为int,如果<i>s</i>是null或"",则返回<i>defaultValue</i>.
	 */
	public static int toInt(String s, int defaultValue) {
		Integer i = toInteger(s);
		if (i == null) {
			return defaultValue;
		} else {
			return i;
		}
	}

	/**
	 * 将List<String>转成List<Integer>,并去掉空字符串或null的项.
	 */
	public static List<Integer> toIntegerList(List<String> s) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < s.size(); i++) {
			Integer inte = toInteger(s.get(i));
			if (inte != null) {
				list.add(inte);
			}
		}
		return list;
	}

	/**
	 * 将String转为Long,空字符串("")转为null.
	 */
	public static Long toLong(String s) {
		if ("".equals(s) || s == null) {
			return null;
		}
		return Long.valueOf(s);
	}

	/**
	 * 将String转为Short,空字符串("")转为null.
	 */
	public static Short toShort(String s) {
		if ("".equals(s) || s == null) {
			return null;
		}
		return Short.valueOf(s);
	}

	/**
	 * 将List<String>转成List<Long>,并去掉空字符串或null的项.
	 */
	public static List<Long> toLongList(List<String> s) {
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < s.size(); i++) {
			Long l = toLong(s.get(i));
			if (l != null) {
				list.add(l);
			}
		}
		return list;
	}

	/**
	 * 将String转为Double,空字符串("")转为null.
	 */
	public static Double toDouble(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		return Double.valueOf(s);
	}

}
