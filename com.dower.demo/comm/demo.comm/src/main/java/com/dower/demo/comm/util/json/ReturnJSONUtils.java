package com.dower.demo.comm.util.json;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回对象
 * 
 * @author liming
 * 
 */
public class ReturnJSONUtils {

	private static final String BODY = "respBody";

	/**
	 * 封装返回对象
	 * 
	 * @param obj
	 * @param type
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> returnJSON(Object obj, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (type) {
		case 0:// 成功
			map.put(BODY, obj);
			return map;
		case 1:// 失败
			map.put(BODY, null);
			return map;
		default:
			map.put(BODY, null);
			return map;
		}
	}

}
