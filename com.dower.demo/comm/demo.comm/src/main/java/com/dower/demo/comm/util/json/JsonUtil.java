package com.dower.demo.comm.util.json;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json工具类
 * @author NiuXueJun
 * 2015-7-31 下午3:23:00
 */
public class JsonUtil {

	
	public static final String EMPTY_JSON_ARRAY="[]";
	 
	public static final String DEFAULT_DATE_PATTERN="yyyy-MM-dd HH:mm:ss SSS";
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private JsonUtil(){
		 super();
	}
	/**
	 * 将Map<String, Object>转成Json串
	 * 
	 * @param map
	 *            Map<String, Object>
	 * @return Json串
	 */
	public static String convert2Json(Map<?, ?> map) {
		// StringWriter不需要关闭IO流（可见Java源码）
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
			return sw.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将Json串转成Map 1
	 * 
	 * @param jsonText
	 *            Json串
	 * @return Map
	 */
	public static Map<?, ?> json2Map(String jsonText) {
		try {
			Map<?, ?> map = mapper.readValue(jsonText, HashMap.class);
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将Json串转成Map 2
	 * 
	 * @param jsonText
	 *            Json串
	 * @return Map
	 */
	public static Map<?, ?> jsonToMap(String jsonText) {
		try {
			JsonFactory jsonFactory = new MappingJsonFactory();
			// Json解析器
			JsonParser jsonParser = jsonFactory.createJsonParser(jsonText);
			// 跳到结果集的开始
			jsonParser.nextToken();
			// 接受结果的HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// while循环遍历Json结果
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				// 跳转到Value
				jsonParser.nextToken();
				// 将Json中的值装入Map中
				map.put(jsonParser.getCurrentName(), jsonParser.getText());

			}
			return map;
		} catch (Exception e) {
			return null;
		}

	}
	
	/**
	 * json转xml
	 * @param json  {"node":{"key":{"@label":"key1","#text":"value1"}}}
	 * @return String <node class="object"><key class="object"
	 * label="key1">value1</key></node>
	 */
	public static String jsontoXml(String jsonStr) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSONObject  jsonObject = JSONObject.fromObject(jsonStr);
			return serializer.write(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {  
        Gson gson = new Gson();
        return gson.toJson(obj);  
    }  
  
    /** 
     * json字符串转成对象 
     * @param json
     * @param datePattern
     * @return  
     */  
    public static <T> T fromJson(String json, TypeToken<T> token,String datePattern) {
    	if (isBlank(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } 
    }  
  
  /**
   *  
   * @param json
   * @param clazz
   * @param datePattern
   * @return
   */
    public static <T> T fromJson(String json, Class<T> clazz,String datePattern) {
       if(isBlank(json)){
    	   return null;
       }
        GsonBuilder builder =new GsonBuilder();
        builder.setDateFormat(datePattern);
        if(isBlank(datePattern)){
        	datePattern=DEFAULT_DATE_PATTERN;
        }
        Gson gson=builder.create();
        gson.fromJson(json, clazz);
        try {
			return gson.fromJson(json, clazz);
		} catch (Exception e) {
		    return null;
		}
    }  
    private static boolean isBlank(String text){
    	return null ==text||"".equals(text.trim());
    }
    
	public static Map<String,Object> jsonToMap2(String jsonStr){
		Map<String,Object> map=new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		Set<Entry<String, ?>>  entrySet = json.entrySet();
		for (Map.Entry<String, ?> e: entrySet) {
			if(e.getValue() instanceof JSONArray){//如果是数组
				map.put(e.getKey(), jsonToList(e.getValue().toString()));
			}else{//不是数组
				map.put(e.getKey(), e.getValue());
			}
		}
		return map;
	}
	
	public static <L> List<L> jsonToList(String jsonStr){
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

}
