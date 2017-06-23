package com.dower.demo.comm.util.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jfree.util.Log;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

/**
 * HTTP上传图片
 * @author NiuXueJun
 * 2015-7-31 下午3:19:48
 */
public class HttpPostUploadUtil {

	/**
	 * 上传图片
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return String
	 */
	public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符  
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text  
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file  
			if (fileMap != null) {
				Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();
					MagicMatch match = Magic.getMagicMatch(file, false, true);
					String contentType = match.getMimeType();

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据  
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
	
	/**
	 * 上传图片
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return String
	 */
	public static String formUploadStream(String urlStr, Map<String, String> textMap, Map<String, byte[]> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符  
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text  
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file  
			if (fileMap != null) {
				Iterator<Map.Entry<String, byte[]>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, byte[]> entry = iter.next();
					String inputName = (String) entry.getKey();
					byte[] data = (byte[]) entry.getValue();
					if (data.length == 0) {
						continue;
					}
//					MagicMatch match = Magic.getMagicMatch(data, false);
//					String contentType = match.getMimeType();
					String contentType = "application/octet-stream";;

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + inputName + "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(strBuf.toString().getBytes());
					out.write(data);
				}
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据  
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
	
	/**
	 * 方法说明: 删除文件
	 * @param path 格式为comm/pro/1441084843085.jpg
	 * @param url	格式为http://localhost:8080/ucp.resource-server/fileProcess/delFile.do
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年9月1日 下午1:16:20
	 */
	public static Boolean delFile(List pathList , String url){
		HttpClient httpClient = new HttpClient();
		PostMethod pm= new PostMethod(url);
		NameValuePair[] data = { new NameValuePair("pathList", pathList.toString())};
		pm.setRequestBody(data);
		pm.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(pm);
			if (statusCode != HttpStatus.SC_OK) {
			   System.err.println("Method failed: "+ pm.getStatusLine());
			}
			byte[] responseBody = pm.getResponseBody();
			String s=new String(responseBody);
			if (s.equals("true")) {
				return true;
			}
		} catch (Exception e) {
			Log.error("网络异常！");
		}finally{
			pm.releaseConnection();
		}
		return false;
	}
	
	/**
	 * 方法说明: 添加文件
	 * @param file
	 * @param url
	 * @return
	 * @author：陈思凡
	 * @QQ：995998760
	 * @date：2015年9月1日 下午4:38:16
	 */
	@SuppressWarnings("unused")
	public Boolean addFile(File file,String url){
		HttpClient httpClient = new HttpClient();
		PostMethod pm = new PostMethod(url+"");
		try {
			FilePart fp=new FilePart("file", file);
			MultipartRequestEntity mre=new MultipartRequestEntity(new  Part[] {fp}, pm.getParams());
			pm.setRequestEntity(mre);
			int statusCode = httpClient.executeMethod(pm);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + pm.getStatusLine());
			}
			byte[] responseBody = pm.getResponseBody();
			String s = new String(responseBody);
			if (s.equals("true")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 上传单个文件
	 * @param b 字节流
	 * @param String url
	 * @param name 名字
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年12月9日下午4:45:06
	 * @return String
	 */
	public static String uploadFile(byte[] b , String url , String name){
		String BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
		try {
			URL u= new URL(url);
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			//设置头部
			huc.setReadTimeout(10*10000000);
			huc.setConnectTimeout(10*10000000);
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setUseCaches(false);
			huc.setRequestMethod("POST");
			huc.setRequestProperty("Charset", "utf-8");
			huc.setRequestProperty("Connection", "keep-alive");
			huc.setRequestProperty("Content-Type", "multipart/form-data;boundary="+BOUNDARY);
			//设置头部结束
			
			DataOutputStream dos = new DataOutputStream(huc.getOutputStream());
			//文件写入准备工作
			StringBuffer sb = new StringBuffer();
			sb.append("--"+BOUNDARY+"\r\n");
			sb.append("Content-Disposition: form-data; name=\""+name+"\"; filename=\"1.jpg\"\r\n");
			sb.append("Content-Type: application/octet-stream; charset=utf-8\r\n" );
			sb.append("\r\n");
			dos.write(sb.toString().getBytes());
			//文件写入准备工作结束
			
			//开始写入文件流
			dos.write(b, 0, b.length);
			//写入文件流结束
			
			//开始结尾工作
			sb.delete(0, sb.length());
			sb.append("\r\n--"+BOUNDARY+"--\r\n");
			dos.write(sb.toString().getBytes());
			//结束结尾工作
			
			//传参结束
			dos.flush();
			
			//获取返回值
			InputStream inStream = huc.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int responseLen = 0;  
	        while( (responseLen = inStream.read(buffer)) != -1 ){  
	            outStream.write(buffer, 0, responseLen);  
	        }  
	        inStream.close();  
			byte[] data = outStream.toByteArray();
	        return new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String filepath = "/testfile/tempImg/aa.jpg";
		String urlStr = "http://192.168.4.109:8086/ucp.resource-server/fileProcess/upload.do";
		Map<String, String> textMap = new HashMap<String, String>();
		textMap.put("uploadPath", "uploadPath1");
		Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
		File file = new File(filepath);
		fileMap.put("userfile111.jpg", getBytesFromFile(file));
		String ret = formUploadStream(urlStr, textMap, fileMap);
		System.out.println(ret);
	}
	
	
	public static byte[] getBytesFromFile(File f){  
        if (f == null){  
            return null;  
        }  
        try{  
            FileInputStream stream = new FileInputStream(f);  
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = stream.read(b)) != -1)  
                out.write(b, 0, n);  
                stream.close();  
                out.close();  
            return out.toByteArray();  
        } catch (IOException e){  
            e.printStackTrace();  
        }  
        return null;  
    }  
}