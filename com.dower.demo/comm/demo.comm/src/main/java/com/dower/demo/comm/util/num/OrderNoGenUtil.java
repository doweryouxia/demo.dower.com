package com.dower.demo.comm.util.num;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.dower.demo.comm.util.MaxLengthMap;

/**
 * 订单编号生成类
 * @author NiuXueJun
 * 2015-7-31 下午3:35:37
 */
public class OrderNoGenUtil {

	private static Map<String, AtomicInteger> orderNoMap = new MaxLengthMap<String, AtomicInteger>(50000);

	public static String genOrderNo(String preOrderNo) {
		AtomicInteger serialNo = orderNoMap.get(preOrderNo);
		int nextNum;
		if (serialNo == null) {
			serialNo = new AtomicInteger(1);
		}
		nextNum = serialNo.getAndIncrement();
		orderNoMap.put(preOrderNo, serialNo);
		String nextNumStr = String.format("%06d", nextNum);
		return preOrderNo + nextNumStr;
	}

}