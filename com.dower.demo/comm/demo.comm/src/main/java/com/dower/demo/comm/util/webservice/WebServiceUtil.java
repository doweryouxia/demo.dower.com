package com.dower.demo.comm.util.webservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

public class WebServiceUtil {
	/**
	 * 访问.net webservice平台工具方法
	 * 
	 * @return String
	 */
	public static String AsmxUtil(String endpoint, String soapaction,
			String method, Map<String, String> paraMap) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(endpoint));
			call.setUseSOAPAction(true);
			// 第二种设置返回值类型为String的方法
			call.setReturnType(new QName("http://www.w3.org/2001/XMLSchema",
					"string"));
			call.setOperationName(new QName(soapaction, method));
			call.setSOAPActionURI(soapaction + method);
			// 设置参数、参数值，遍历MAP
			Object[] paraValues = new Object[paraMap.size()];
			Set<String> key = paraMap.keySet();
			// 计数器，用于设置数组的值ֵ
			int paraValuesIndex = 0;
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String paraName = (String) it.next();
				call.addParameter(new QName(soapaction, paraName),// 设置参数名
						XMLType.XSD_STRING, ParameterMode.IN);
				String paraValue = paraMap.get(paraName);
				paraValues[paraValuesIndex] = paraValue;// 设置参数值ֵ
				paraValuesIndex++;
			}
			String retVal2 = (String) call.invoke(paraValues);
			return retVal2;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * String format 根据后面传的参数格式化第一个参数的字符串.
	 * 
	 * @param str
	 * @param args
	 * @return 格式化后的字符串.
	 */
	public static String format(String str, Object... args) {
		if (str == null || "".equals(str))
			return "";
		if (args.length == 0) {
			return str;
		}
		String result = str;
		java.util.regex.Pattern p = java.util.regex.Pattern
				.compile("\\{(\\d+:{0,1}@{0,1}\\w*)\\}");
		java.util.regex.Matcher m = p.matcher(str);

		while (m.find()) {
			int index = Integer.parseInt(m.group(1));
			if (index < args.length) {
				result = result.replace(m.group(), args[index].toString());
			}
		}
		return result;
	}

	/**
	 * 发送请求 ，返回数据
	 * 
	 * @param TMPLATE
	 *            模板
	 * @param args
	 *            需要的参数
	 * @return String
	 * @throws Exception
	 */
	public static String sendPostRequsetForInfo(String url, String TMPLATE,
			Object... args) throws Exception {
		String soapRequestData = format(TMPLATE, args);
		System.err.println(soapRequestData);
		PostMethod postMethod = new PostMethod(url);
		byte[] b = soapRequestData.getBytes("utf-8");
		InputStream is = new ByteArrayInputStream(b, 0, b.length);
		RequestEntity re = new InputStreamRequestEntity(is, b.length,
				"application/soap+xml; charset=utf-8");
		postMethod.setRequestEntity(re);
		HttpClient httpClient = new HttpClient();
		httpClient.executeMethod(postMethod);
		String soapResponseData = postMethod.getResponseBodyAsString();
		return soapResponseData;
	}
}
