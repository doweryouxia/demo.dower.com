package util.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.xml.XmlConverUtil;

class Statudent{
	
	private String name;

	private int age;

	private List<String> books = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getBooks() {
		return books;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}
}

public class TestXmlConverUtil {

	private static String XML_STR = null;
	
	private static Map<String,Object> map = new HashMap<String,Object>();
	
	private static Statudent test = null;
	
	private static String EXPORT_XML_FILE ="";
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestXmlConverUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		properties.load(in);
		EXPORT_XML_FILE = properties.getProperty("export.xml.file");
		File file = new File(EXPORT_XML_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
		
		XML_STR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><util.xml.Statudent><name>张三</name><age>3</age><books><string>design parttern</string><string>agile</string><string>java</string><string>asp</string></books></util.xml.Statudent>";

		test = new Statudent();
		test.setAge(3);
		test.setName("张三");
		test.getBooks().add("design parttern");
		test.getBooks().add("agile");
		test.getBooks().add("java");
		test.getBooks().add("asp");	
		
		map.put("1","aa");
		map.put("2","bb");
		map.put("statudent", test);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToXml() {
		String xmlStr = XmlConverUtil.toXml(test);
		System.out.println(xmlStr);
	}

	@Test
	public void testToBean() {
		Statudent bean = XmlConverUtil.toBean(XML_STR, Statudent.class);
		System.out.println(bean.getName());
	}

	@Test
	public void testToXMLFile() {
		String fileName = "TestXmlConverUtil.xml";
		boolean ret  = XmlConverUtil.toXMLFile(test, EXPORT_XML_FILE, fileName);
		System.out.println(ret);
	}

	@Test
	public void testMaptoXml() {
		
		String mapXmlStr = XmlConverUtil.maptoXml(map);
		System.out.println("map to xml:"+mapXmlStr);
	}

	@Test
	public void testListtoXml() {
		List list = new ArrayList();
		list.add("aa");
		list.add("bb");
		list.add(test);
		try {
			String listXmlStr = XmlConverUtil.listtoXml(list);
			System.out.println("list to xml:"+listXmlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testXmltoMap() {
		String mapXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><node><key label=\"2\">bb</key><key label=\"1\">aa</key></node>";
		Map map = XmlConverUtil.xmltoMap(mapXmlStr);
		for(Object key:map.keySet()){
			System.out.println(key.toString()+"=="+map.get(key));
		}
	}

	@Test
	public void testXmltoList() {
		List<Map> list = XmlConverUtil.xmltoList(XML_STR);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			for(Object obj:map.keySet()){
				System.out.println(obj+"=>>"+map.get(obj));
			}
		}
	}

	@Test
	public void testXmltoJson() {
		String jsonStr = XmlConverUtil.xmltoJson(XML_STR);
		System.out.println("jsonStr:"+jsonStr);
	}

}
