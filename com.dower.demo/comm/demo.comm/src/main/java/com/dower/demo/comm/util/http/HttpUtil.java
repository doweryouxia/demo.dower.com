package com.dower.demo.comm.util.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	/**
	 * HTTP发送post请求
	 * @param path 请求地址
	 * @param params 参数
	 * @return String
	 * @throws Exception
	 */
	public static String sendPostRequestByParam(String path, String params) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");// 提交模式
		conn.setDoOutput(true);// 是否输入参数
		byte[] bypes = params.toString().getBytes();
		conn.getOutputStream().write(bypes);// 输入参数
		InputStream inStream = conn.getInputStream();
		return new String(readInputStream(inStream));
	}

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
}
