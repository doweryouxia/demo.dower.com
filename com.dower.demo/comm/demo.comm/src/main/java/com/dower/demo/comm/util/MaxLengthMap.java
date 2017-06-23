package com.dower.demo.comm.util;

/**
 * Created by lee on 15/1/26.
 */
import java.util.LinkedHashMap;
import java.util.Map;

public class MaxLengthMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	private long maxCount = 20000;

	public MaxLengthMap(long maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		if (this.size() > maxCount) {
			return true;
		}
		return false;
	}
}