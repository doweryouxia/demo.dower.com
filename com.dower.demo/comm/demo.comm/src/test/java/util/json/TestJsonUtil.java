package util.json;

import java.util.HashMap;
import java.util.Map;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.json.JsonUtil;

 class User {  
    
    private String username;  
    private String password;  
      
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
      
}
 
public class TestJsonUtil {
	
	private static String jsonStr = null;
	private static Map<String,Object> map = new HashMap<String, Object>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		jsonStr = "{\"name\":\"Michael\",\"address\":{\"city\":\"Suzou\",\"street\":\" Changjiang Road \",\"postcode\":\"100025\"},\"blog\":\"http://www.ij2ee.com\"}";
		
		User user = new User(); 
		user.setUsername("cxl"); 
		user.setPassword("1234"); 
	
		map.put("user", user);
		map.put("userId", 1001);
		map.put("userName", "张三");
		map.put("userSex", "男");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testConvert2Json() {
		System.out.println(JsonUtil.convert2Json(map));
	}

	@Test
	public void testJson2Map() {
		Map<String,Object> map = (Map<String, Object>) JsonUtil.json2Map(jsonStr);
		for (String key : map.keySet()) {
			System.out.println("json2Map:"+key+"=>>"+map.get(key));
		}
	}

	@Test
	public void testJsonToMap() {
		Map<String,Object> map = (Map<String, Object>) JsonUtil.jsonToMap(jsonStr);
		for (String key : map.keySet()) {
			System.out.println("jsonToMap:"+key+"=>>"+map.get(key));
		}
	}

	@Test
	public void testJsontoXml() {
		String xml = JsonUtil.jsontoXml(jsonStr);
		System.out.println(xml);
	}

}
