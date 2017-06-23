package com.dower.demo.comm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 服务工具类
 * @author NiuXueJun
 * 2015-7-31 下午3:36:52
 */
public class ServiceUtil {
	/**
	 * 返回首字母
	 * 
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		String temp = "";
		String demo = "";
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		for (int i = 0; i < convert.length(); i++) {// convert目前为小写首字母,下面是将小写首字母转化为大写
			if (convert.charAt(i) >= 'a' && convert.charAt(i) <= 'z') {
				temp = convert.substring(i, i + 1).toUpperCase();
				demo += temp;
			}
		}
		return demo;
	}

	/**
	 * 获取真实IP
	 * 
	 * @param request
	 * @return String
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 判断 是否是数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取浏览器类型
	 * 
	 * @param request
	 * @return String
	 */
	public static String getBrowser(HttpServletRequest request) {
		String[] browsers = { "MSIE", "Firefox", "Safari", "Chrome", "Opera",
				"360SE", "QQBrowser" };
		if (request.getHeader("User-Agent") != null) {
			for (String browser : browsers) {
				if (request.getHeader("User-Agent").indexOf(browser) >= 0) {
					return browser;
				}
			}
		}
		return "other";
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			// 強行刪除
			System.gc();
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 扫描文件夹下是否存在驾照照片
	 * 
	 * @param path
	 * @param filename
	 * @return boolean
	 */
	public static boolean scanFile(String path, String filename) {
		File file = new File(path);
		File[] filelist = file.listFiles();
		for (File f : filelist) {
			if (filename.equals(f.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 保留两位
	 * 
	 * @param a
	 * @return String
	 */
	public static String get2Double(double a) {
		BigDecimal result = new BigDecimal(a).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		return result + ".00";
	}

	/**
	 * 验证url
	 * 
	 * @param urlString
	 * @return boolean
	 */
	public static boolean isURL(String urlString) {
		String regex = "(http|https):\\/\\/([\\w.]+\\/?)\\S*";
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(urlString);
		boolean isMatch = matcher.matches();
		return isMatch;
	}

	/**
	 * 判断传入是否为null
	 * 
	 * @param fieldValue
	 * @return String
	 */
	public static String isNUll(String fieldValue) {
		if (fieldValue != null && fieldValue.length() > 0) {
			return fieldValue;
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @param srcfile
	 *            文件名数组
	 * @param zipfile
	 *            压缩后文件
	 */
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹下全部文件
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除文件夹下全部文件
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对list进行假分页
	 * 
	 * @param list
	 * @param pageSize
	 * @return List<List<T>>
	 */
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		int listSize = list.size();
		int page = (listSize + (pageSize - 1)) / pageSize;
		List<List<T>> listArray = new ArrayList<List<T>>();
		for (int i = 0; i < page; i++) {
			List<T> subList = new ArrayList<T>();
			for (int j = 0; j < listSize; j++) {
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
				if (pageIndex == (i + 1)) {
					subList.add(list.get(j));
				}
				if ((j + 1) == ((j + 1) * pageSize)) {
					break;
				}
			}
			listArray.add(subList);
		}
		return listArray;
	}

	/**
	 * MD5加密
	 * 
	 * @param s
	 * @return String
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		s = UUID.randomUUID().toString() + s;
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).substring(8, 20);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public final static String MD5Two(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).substring(8, 20);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据传入排量数据，是否需要解析
	 * 
	 * @param engineOutput
	 * @return String
	 */
	public static String getEngineOutput(String engineOutput, String path) {
		if ((engineOutput.indexOf("大于") != -1)
				|| (engineOutput.indexOf("小于") != -1)
				|| (engineOutput.indexOf("等于") != -1)) {
			// 读取配置文件中数据并匹配
			File f = null;
			InputStreamReader is = null;
			BufferedReader br = null;
			try {
				f = new File(path + "engineOutput.txt");
				is = new InputStreamReader(new FileInputStream(f), "gbk");
				br = new BufferedReader(is);
				while (br.ready()) {
					// 排量需要分割
					String engineOutputTemp = br.readLine();
					if (engineOutputTemp.indexOf("|") != -1) {
						String[] engineOutputArray = engineOutputTemp
								.split("[|]");
						if (engineOutput.endsWith(engineOutputArray[0])) {
							return engineOutputArray[1];
						}
					}
				}
				br.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
				return "1.0";
			}
		} else {
			return engineOutput;
		}
		return "1.0";
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param startDate
	 *            较小的时间
	 * @param endDate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送HttpPost请求
	 * 
	 * @param strURL
	 *            服务地址
	 * @param params
	 *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
	 * @return 成功:返回json字符串<br/>
	 */
	public static String sendPostJSON(String strURL, String params) {
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				return result;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}
//=================================================================
	/**
	 * 根据vin返回排量 确定返回字符串
	 * 
	 * @param engineOutputStr
	 * @param engineOutput
	 * @return boolean
	 */
	public static boolean getEngineOutputStr(String engineOutputStr,
			String engineOutput) {
		double engineOutputDouble = Double.parseDouble(engineOutput);
		String[] engineOutputStrArray = engineOutputStr.split("L");
		if (engineOutputStrArray.length == 2) {
			double engineOutput1 = Double.parseDouble(engineOutputStrArray[0]
					.split("大于")[1]);
			double engineOutput2 = Double.parseDouble(engineOutputStrArray[1]
					.split("等于")[1]);
			if (engineOutputDouble > engineOutput1
					&& engineOutputDouble <= engineOutput2) {
				return true;
			}
		} else {
			if (engineOutputStrArray[0].indexOf("大于") != -1) {
				double engineOutput1 = Double
						.parseDouble(engineOutputStrArray[0].split("等于")[1]);
				if (engineOutputDouble >= engineOutput1) {
					return true;
				}
			} else if (engineOutputStrArray[0].indexOf("小于") != -1) {
				double engineOutput1 = Double
						.parseDouble(engineOutputStrArray[0].split("等于")[1]);
				if (engineOutputDouble <= engineOutput1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 前一天
	 * @param _date
	 * @return Date
	 */
	public static Date getBeforeDay(Date _date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(_date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		_date = calendar.getTime();
		return _date;
	}

	/**
	 * 后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 转换时间
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String changeTimestampToString(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String timeStr = sdf.format(timestamp);
		return timeStr;
	}
}
