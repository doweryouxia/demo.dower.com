package com.dower.demo.app.vc.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;


/**
 * 读取配置文件
 * @author lyy
 */

public class SystemInfo {

	private static Logger logger = LoggerFactory.getLogger(SystemInfo.class);

	private static Properties properties;

	public static String UPLOADFILE_PATH;//存放上传文件地址

	
	static {
		try {
			properties = new Properties();
			InputStream is = SystemInfo.class.getClassLoader().getResource("systemInfo.properties").openStream();
			properties.load(is);

			UPLOADFILE_PATH = getString("upload.file.path","");

			
		} catch (Exception e) {
			logger.error("加载属性文件失败",e);
		}
	}

	private static String getString(String key, String defaultValue) {
		String value = properties.getProperty(key);
		if (null != value && !"".endsWith(value)) {
			return value.trim();
		} else {
			return defaultValue;
		}
	}

	private static String getString(String key, String defaultValue, String prefix) {
		String value = properties.getProperty(key);
		if (null != value && !"".endsWith(value)) {
			return prefix + value.trim();
		} else {
			return defaultValue;
		}
	}

	private static int getInt(String key, int defaultValue) {
		String value = properties.getProperty(key);
		if (null != value) {
			return Integer.parseInt(value);
		} else {
			return defaultValue;
		}
	}

}
