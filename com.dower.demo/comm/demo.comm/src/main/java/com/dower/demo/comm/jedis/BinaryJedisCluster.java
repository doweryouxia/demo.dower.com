package com.dower.demo.comm.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.dower.demo.comm.util.SerializationUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class BinaryJedisCluster extends JedisCluster {
	
	static Logger log = LoggerFactory.getLogger(BinaryJedisCluster.class);
	
	public BinaryJedisCluster(Set<HostAndPort> nodes) {
		super(nodes);
	}
	
	public BinaryJedisCluster(Set<HostAndPort> nodes, int timeout) {
		super(nodes, timeout);
	}
	public BinaryJedisCluster(Set<HostAndPort> jedisClusterNode,
			int timeout, int maxRedirections, GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, timeout, maxRedirections, poolConfig);
	}
	

	
	
	public <V> Long hseto(String key, String field, V value) {
		return super.hset(key, field, rawValue(value));
	}

	public <V> Long hsetnxo(String key, String field, V value) {
		return super.hset(key, field, rawValue(value));
	}
	
	public <V> String hmseto(String key, Map<String, V> hash) {
		return super.hmset(key, rawMap(hash));
	}
	
	public <HV> HV hgeto(String key, String field) {
		return derawValue(super.hget(key, field));
	};
	
	public <V> List<V> hmgeto(String key, String... fields) {
		Assert.notEmpty(fields, "hmgeto方法fields字段不能为空");
		return derawListValue(super.hmget(key, fields));
	}
	
	public  <V> Map<String, V> hgetAllo(String key) {
		return derawMap(super.hgetAll(key));
	}
	
	public  <V> List<V> hvalso(String key) {
		return derawListValue(super.hvals(key));
	}
	
	/**
	 * 得到hash序列号
	 * @param key
	 * @param field
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日下午2:02:28
	 * @return Long
	 */
	public Long getHSerialNumber(String key,String field){
		return super.hincrBy(key, field, 1L);
	}
	
	
	/**
	 * 序列化object成string
	 * @param value
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日上午10:41:19
	 * @return String
	 */
	<V> String rawValue(V value){
		try {
			return new String(serializeObject(value),"ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把object[]序列化成string[]
	 * @param value
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日下午1:23:36
	 * @return String[]
	 */
	<V> String[] rawValue(V[] value){
		String[] vs= new String[value.length];
		int i=0;
		for (V v : value) {
			vs[i++] = rawValue(v);
			if(vs[i-1]==null){
				return null;
			}
		}
		return vs;
	}

	/**
	 * 序列化Map<String, V>成Map<String, String>
	 * @param hash
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日上午11:52:38
	 * @return Map<String,String>
	 */
	<V> Map<String, String> rawMap(Map<String, V> hash){
		Assert.notNull(hash, "不能为空");
		try {
			final Map<String, String> hashes = new LinkedHashMap<String, String>(hash.size());
			for (Map.Entry<String, V> entry : hash.entrySet()) {
				hashes.put(entry.getKey(), new String(serializeObject(entry.getValue()),"ISO-8859-1"));
			}
			return hashes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 反序列化string成object
	 * @param value
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日上午10:41:52
	 * @return HV
	 */
	<HV> HV derawValue(String value) {
		try {
			if(StringUtils.isEmpty(value))
				return null;
			return deSerializeObject(value.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 反序列化List<String>成List<object>
	 * @param listValue
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日下午1:38:06
	 * @return List<V>
	 */
	@SuppressWarnings("unchecked")
	<V> List<V> derawListValue(List<String> listValue) {
		return deserializeValues(listValue, List.class);
	}
	
	/**
	 * 反序列化Map<String, String>成Map<String, V>
	 * @param hash
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月13日下午1:43:18
	 * @return Map<String,V>
	 */
	<V> Map<String, V> derawMap(Map<String, String> hash){
		try {
			if (hash == null) {
				return null;
			}
			final Map<String, V> hashes = new LinkedHashMap<String, V>(hash.size());
			for (Map.Entry<String, String> entry : hash.entrySet()) {
				hashes.put(entry.getKey(), (V) deSerializeObject(entry.getValue().getBytes("ISO-8859-1")));
			}
			return hashes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	byte[] serializeObject(Object obj) throws Exception{
		Assert.notNull(obj, "不能为空");
		if(obj instanceof byte[]){
			return (byte[]) obj;
		}
		return SerializationUtils.kryoSerialize(obj);
	}
	
	@SuppressWarnings("unchecked")
	<HV> HV deSerializeObject(byte[] value) throws Exception {
		Assert.notNull(value, "不能为空");
		return (HV) SerializationUtils.kryoDeserialize(value);
	}

	@SuppressWarnings("unchecked")
	<T extends Collection<?>> T deserializeValues(Collection<String> rawValues, Class<T> type) {
		try {
			if (rawValues == null) {
				return null;
			}
			Collection<Object> values = (List.class.isAssignableFrom(type) ? new ArrayList<Object>(rawValues.size())
					: new LinkedHashSet<Object>(rawValues.size()));
			for (String s : rawValues) {
				values.add(s!=null?deSerializeObject(s.getBytes("ISO-8859-1")):null);
			}
			return (T) values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * json转换成map
	 * @param jsonStr
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月17日下午3:18:47
	 * @return Map<String,Object>
	 */
	static Map<String,Object> jsonToMap(String jsonStr){
		try {
			if(StringUtils.isEmpty(jsonStr)){
				return null;
			}
			Pattern p = Pattern.compile("^\\{.*\\}$");
			Matcher matcher = p.matcher(jsonStr);
			if(matcher.find())
				return jsonToMap2(jsonStr);//json转map
			else
				return StringToMap(jsonStr);//执行默认的string转map
		} catch (Exception e) {
			e.printStackTrace();
			log.error("无法解析的数据类型");
			return null;
		}
	}
	
	/**
	 * json转list
	 * @param jsonStr
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月18日下午1:51:30
	 * @return List<L>
	 */
	static <L> List<L> jsonToList(String jsonStr){
		try {
			if(StringUtils.isEmpty(jsonStr)){
				return null;
			}
			Pattern p = Pattern.compile("^\\[.*\\]$");
			Matcher matcher = p.matcher(jsonStr);
			if(matcher.find())
				return jsonToList2(jsonStr);//json转List
			else
				return StringToList(jsonStr);//执行默认的string转List
		} catch (Exception e) {
			e.printStackTrace();
			log.error("无法解析的数据类型");
			return null;
		}
	}
	
	/**
	 * json转map或者list
	 * @param jsonStr
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年11月18日下午2:11:02
	 * @return O
	 */
	@SuppressWarnings("unchecked")
	public static <O> O jsonToObject(String jsonStr){
		try {
			if(StringUtils.isEmpty(jsonStr)){
				return null;
			}
			Pattern p = Pattern.compile("^\\[.*\\]$");
			Matcher matcher = p.matcher(jsonStr);
			if(matcher.find())
				return (O) jsonToList2(jsonStr);//json转List
			else
				return (O) jsonToMap2(jsonStr);//json转map
		} catch (Exception e) {
			e.printStackTrace();
			log.error("无法解析的数据类型");
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	static Map<String,Object> jsonToMap2(String jsonStr){
		Map<String,Object> map=new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		Set<Entry<String, ?>>  entrySet = json.entrySet();
		for (Map.Entry<String, ?> e: entrySet) {
			if(e.getValue() instanceof JSONArray){//如果是数组
				map.put(e.getKey(), jsonToList2(e.getValue().toString()));
			}else{//不是数组
				map.put(e.getKey(), e.getValue());
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	static <L> List<L> jsonToList2(String jsonStr){
		List<Object> list= new ArrayList<Object>();
		JSONArray fromObject = JSONArray.fromObject(jsonStr);
		for (Object object : fromObject) {
			if(object instanceof JSONObject)
				list.add(jsonToMap2(object.toString()));
			else
				list.add(object.toString());
		}
		return (List<L>)list;
	}

	//str格式为“a:1,b:2,c:3”……
	static Map<String,Object> StringToMap(String str){
		Map<String,Object> map=new HashMap<String, Object>();
		for (String string : str.split(",")) {
			String[] s=string.split(":");
			map.put(s[0], s[1]);
		}
		return map;
	}
	
	//json格式为a,b,c,d……
	@SuppressWarnings("unchecked")
	public static <L> List<L> StringToList(String json){
		List<String> list= new ArrayList<String>();
		for (String string : json.split(",")) {
			list.add(string);
		}
		return (List<L>) list;
	}
	
	//String数组转换成String
	@SuppressWarnings("unchecked")
	public static String ListToString(String... str){
		StringBuffer sb=new StringBuffer();
		for (String string : str) {
			sb.append(string);
			sb.append(",");
		}
		return sb.substring(0, sb.length()-1);
	}

}
