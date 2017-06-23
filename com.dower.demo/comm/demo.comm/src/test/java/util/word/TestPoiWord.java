package util.word;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPoiWord {
	
	private static String EXPORT_WORD_FILE ="";
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestPoiWord.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		properties.load(in);
		EXPORT_WORD_FILE = properties.getProperty("export.word.file");
		File file = new File(EXPORT_WORD_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSearchAndReplace() {
		try {
			HashMap map=new HashMap();
			map.put("${title}", "正是标题");
			map.put("${orderNum}","NO111111111111111");
			map.put("${vc2userMobile}","111111");
			map.put("${StYear}","9999999999999999");

			String srcPath = EXPORT_WORD_FILE+"hetong.docx";
			String destPath = EXPORT_WORD_FILE+"hetong_res.doc";

		//	PoiWord.searchAndReplace(srcPath ,destPath,map);

//		    PoiWord.poiReadWordTitleDocx(srcPath,destPath,map);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
