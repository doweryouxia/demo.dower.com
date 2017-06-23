package com.dower.demo.comm.util.urlcode;

import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.xmlbeans.impl.util.Base64;

/**
 * 编码或解码URL工具类
 * @author NiuXueJun
 * 2015-7-31 下午2:37:10
 */
public class CodingUtils {
	
	private static final Base64 base64 = new Base64();
	
	/**
	 * Base64编码URL
	 * @param arg
	 * @return String
	 * @throws Exception
	 */
	public static String encodeBase64URL(String arg) throws Exception {
		String content = new String(Base64.encode(URLEncoder.encode(arg, "UTF-8").getBytes()));
		return content;
	}

	/**
	 * Base64解码URL
	 * @param arg
	 * @return String
	 * @throws Exception
	 */
	public static String decodeBase64URL(String arg) throws Exception {
		String content = URLDecoder.decode(new String(base64.decode(arg.getBytes())), "UTF-8");
		return content;
	}
	
	/**
	 * URL编码
	 * @param arg
	 * @return String
	 * @throws Exception
	 */
	public static String encodeURL(String arg) throws Exception {
		String content = URLEncoder.encode(arg, "UTF-8");
		return content;
	}

	/**
	 * URL解码
	 * @param arg
	 * @return String
	 * @throws Exception
	 */
	public static String decodeURL(String arg) throws Exception {
		String content = URLDecoder.decode(arg, "UTF-8");
		return content;
	}

}
