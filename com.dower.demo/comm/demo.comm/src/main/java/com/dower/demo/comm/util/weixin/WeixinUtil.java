package com.dower.demo.comm.util.weixin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.dower.demo.comm.util.http.HttpRequestUtil;

/**
 * 微信工具类
 * @author NiuXueJun
 * 2015-7-31 下午1:58:59
 */
public class WeixinUtil {
	private static Map<String, Object> weixinMap = new HashMap<String, Object>();

	public static Map<String, Object> fileType = new HashMap<String, Object>();
	
	static{
		fileType.put("image/jpeg", ".jpg");
		fileType.put("image/gif", ".gif");
		fileType.put("image/png", ".pbg");
		fileType.put("application/x-bmp", ".bmp");
	}
	
	/**
	 * 获取缓存中的Token
	 * @param appid 
	 * @param secret
	 * @param url
	 * @return String
	 */
	public static String getAccessToken(String appid, String secret, String url) {
		String accessToken = "";
		if(weixinMap.containsKey("tokenTimeMillis")) {
			//上次获取token的时间
			long tokenTimeMillis = (Long) weixinMap.get("tokenTimeMillis");
			//token有效期
			int tokenExpiresIn = (Integer) weixinMap.get("tokenExpiresIn");
			//token未过期
			if(System.currentTimeMillis() - tokenTimeMillis < tokenExpiresIn) {
				accessToken = weixinMap.get("accessToken").toString();
			}
		}
		//未获取缓存中的数据,重新获取token
		if(StringUtils.isBlank(accessToken)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("grant_type", "client_credential");
			map.put("appid", appid);
			map.put("secret", secret);
			String returnXml = HttpRequestUtil.request(url, map, false);
			int tokenExpiresIn = 0;
			if(StringUtils.isNotBlank(returnXml)) {
				try {
					JSONObject jo = new JSONObject(returnXml);
					if(jo.isNull("errcode")) {
						accessToken = jo.getString("access_token");
						tokenExpiresIn = jo.getInt("expires_in");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotBlank(accessToken)) {
					//记录当前时间
					weixinMap.put("tokenTimeMillis", System.currentTimeMillis());
					weixinMap.put("accessToken", accessToken);
					//设置为提前60秒过期(防止获取token到使用这段时间过期),毫秒
					weixinMap.put("tokenExpiresIn", (tokenExpiresIn - 60) * 1000);
				}
			}
		}
		return accessToken;
	}

	/**
	 * 获取微信js临时票据jsapi_ticket
	 * @param appid
	 * @param secret
	 * @param tokenUrl	获取token的url
	 * @param url		获取jsapi_ticket的url
	 * @return String
	 */
	public static String getJsapiTicket(String appid, String secret, String tokenUrl, String url) {
		String jsapiTicket = "";
		if(weixinMap.containsKey("jsapiTicketTimeMillis")) {
			//上次获取jsapiTicket的时间
			long jsapiTicketTimeMillis = (Long) weixinMap.get("jsapiTicketTimeMillis");
			//jsapiTicket有效期
			int jsapiTicketExpiresIn = (Integer) weixinMap.get("jsapiTicketExpiresIn");
			//jsapiTicket未过期
			if(System.currentTimeMillis() - jsapiTicketTimeMillis < jsapiTicketExpiresIn) {
				jsapiTicket = weixinMap.get("jsapiTicket").toString();
			}
		}
		//未获取缓存中的数据,重新获取jsapiTicket
		if(StringUtils.isBlank(jsapiTicket)) {
			//获取token
			String accessToken = getAccessToken(appid, secret, tokenUrl);
			if(StringUtils.isNotBlank(accessToken)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", accessToken);
				map.put("type", "jsapi");
				String returnXml = HttpRequestUtil.request(url, map, false);
				int jsapiTicketExpiresIn = 0;
				if(StringUtils.isNotBlank(returnXml)) {
					try {
						JSONObject jo = new JSONObject(returnXml);
						int errcode = jo.getInt("errcode");
						if(0 == errcode) {
							jsapiTicket = jo.getString("ticket");
							jsapiTicketExpiresIn = jo.getInt("expires_in");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(StringUtils.isNotBlank(jsapiTicket)) {
						//记录当前时间
						weixinMap.put("jsapiTicketTimeMillis", System.currentTimeMillis());
						weixinMap.put("jsapiTicket", jsapiTicket);
						//设置为提前60秒过期(防止获取token到使用这段时间过期),毫秒
						weixinMap.put("jsapiTicketExpiresIn", (jsapiTicketExpiresIn - 60) * 1000);
					}
				}
			}
		}
		return jsapiTicket;
	}
	
	
	
	/**
	 * 获取缓存中的Token
	 * @param appid 
	 * @param secret
	 * @param url
	 * @return String
	 */
	public static Map<String, Object> getAccessToken(String appid, String secret, String url, Map<String, Object> weixinMap) {
		String accessToken = "";
		if(weixinMap.containsKey("tokenTimeMillis")) {
			//上次获取token的时间
			long tokenTimeMillis = (Long) weixinMap.get("tokenTimeMillis");
			//token有效期
			int tokenExpiresIn = (Integer) weixinMap.get("tokenExpiresIn");
			//token未过期
			if(System.currentTimeMillis() - tokenTimeMillis < tokenExpiresIn) {
				accessToken = weixinMap.get("accessToken").toString();
			}
		}
		//未获取缓存中的数据,重新获取token
		if(StringUtils.isBlank(accessToken)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("grant_type", "client_credential");
			map.put("appid", appid);
			map.put("secret", secret);
			String returnXml = HttpRequestUtil.request(url, map, false);
			int tokenExpiresIn = 0;
			if(StringUtils.isNotBlank(returnXml)) {
				try {
					JSONObject jo = new JSONObject(returnXml);
					if(jo.isNull("errcode")) {
						accessToken = jo.getString("access_token");
						tokenExpiresIn = jo.getInt("expires_in");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(StringUtils.isNotBlank(accessToken)) {
					//记录当前时间
					weixinMap.put("tokenTimeMillis", System.currentTimeMillis());
					weixinMap.put("accessToken", accessToken);
					//设置为提前60秒过期(防止获取token到使用这段时间过期),毫秒
					weixinMap.put("tokenExpiresIn", (tokenExpiresIn - 60) * 1000);
				}
			}
		}
		return weixinMap;
	}

	/**
	 * 获取微信js临时票据jsapi_ticket
	 * @param appid
	 * @param secret
	 * @param tokenUrl	获取token的url
	 * @param url		获取jsapi_ticket的url
	 * @return String
	 */
	public static Map<String, Object> getJsapiTicket(String appid, String secret, String tokenUrl, String url, Map<String, Object> weixinMap) {
		String jsapiTicket = "";
		if(weixinMap.containsKey("jsapiTicketTimeMillis")) {
			//上次获取jsapiTicket的时间
			long jsapiTicketTimeMillis = (Long) weixinMap.get("jsapiTicketTimeMillis");
			//jsapiTicket有效期
			int jsapiTicketExpiresIn = (Integer) weixinMap.get("jsapiTicketExpiresIn");
			//jsapiTicket未过期
			if(System.currentTimeMillis() - jsapiTicketTimeMillis < jsapiTicketExpiresIn) {
				jsapiTicket = weixinMap.get("jsapiTicket").toString();
			}
		}
		//未获取缓存中的数据,重新获取jsapiTicket
		if(StringUtils.isBlank(jsapiTicket)) {
			//获取token
			weixinMap = getAccessToken(appid, secret, tokenUrl, weixinMap);
			String accessToken = weixinMap.get("accessToken").toString();
			if(StringUtils.isNotBlank(accessToken)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", accessToken);
				map.put("type", "jsapi");
				String returnXml = HttpRequestUtil.request(url, map, false);
				int jsapiTicketExpiresIn = 0;
				if(StringUtils.isNotBlank(returnXml)) {
					try {
						JSONObject jo = new JSONObject(returnXml);
						int errcode = jo.getInt("errcode");
						if(0 == errcode) {
							jsapiTicket = jo.getString("ticket");
							jsapiTicketExpiresIn = jo.getInt("expires_in");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(StringUtils.isNotBlank(jsapiTicket)) {
						//记录当前时间
						weixinMap.put("jsapiTicketTimeMillis", System.currentTimeMillis());
						weixinMap.put("jsapiTicket", jsapiTicket);
						//设置为提前60秒过期(防止获取token到使用这段时间过期),毫秒
						weixinMap.put("jsapiTicketExpiresIn", (jsapiTicketExpiresIn - 60) * 1000);
					}
				}
			}
		}
		return weixinMap;
	}
}
