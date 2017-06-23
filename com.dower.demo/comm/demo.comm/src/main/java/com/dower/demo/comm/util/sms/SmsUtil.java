package com.dower.demo.comm.util.sms;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.dower.demo.comm.util.http.HttpRequestUtil;
import com.dower.demo.comm.util.urlcode.CodingUtils;

/**
 * 发送短信
 * @author NiuXueJun
 * 2015-7-31 下午3:26:49
 */
public class SmsUtil {
	public static String send(String mobile, String content) {
		// http服务端url
		String httpPath = "http://58.68.247.137:9053/communication/sendSms.ashx";
		Map<String, String> paramMap = new HashMap<String, String>(); 
		// 客户端ID
		String cid = "8208";
		// 客户端密码
		String pwd = "123456";
		// 通道组id
		String productid = "20140829";
		// 手号机
		// String mobile = "18611772187";
		// long mobile=15110013520;13521297871
		// 短信内容3
		// String content = "验证码是5213，【花乡汽车延保】";
		// 子号码
		String lcode = "";
		// 短信唯一标识,用于匹配状态报告
		String ssid = "";
		// 短信类型:15普通短信,32长短信
		String format = "15";
		// 客户自定义签名,可以不填
		String sign = "";
		// 客户自定义内容,目前没有用到,不用填写
		String custom = "";
		int a;
		for (a = 0; a < 1; a++) {
			paramMap = new HashMap<String, String>(); 
			try {
				paramMap.put("cid", CodingUtils.encodeBase64URL(cid));
				paramMap.put("pwd", CodingUtils.encodeBase64URL(pwd));
				paramMap.put("productid", CodingUtils.encodeURL(productid));
				paramMap.put("mobile", CodingUtils.encodeBase64URL(mobile + ""));
				paramMap.put("content", CodingUtils.encodeBase64URL(content));
				paramMap.put("lcode", lcode);
				paramMap.put("ssid", ssid);;
				paramMap.put("format", format);
				paramMap.put("sign", CodingUtils.encodeBase64URL(sign));
				paramMap.put("custom", CodingUtils.encodeURL(custom));
				String result = HttpRequestUtil.request(httpPath, paramMap, true);
				JSONObject jo = new JSONObject(result);
				return jo.getString("status");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "";

	}

}
