package util.file;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.file.FileUtil;

public class TestFileUtil {

	private static String RESOURCE_FILE = "";
	
	private static String TARGET_FILE = "";
	
	private static String TEMP_IMAGE_FILE = "";
	
	private static Properties properties;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestFileUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties  = new Properties();
		properties.load(in);
		RESOURCE_FILE = properties.getProperty("resource.file");
		TARGET_FILE = properties.getProperty("target.file");
		TEMP_IMAGE_FILE = properties.getProperty("temp.image.file");
		
		File file = new File(TARGET_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
		
		File file1 = new File(TEMP_IMAGE_FILE);
		if(!file1.exists()){
			file1.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void testCopyFile() {
		//fail("Not yet implemented");
		File sourceFile = new File(RESOURCE_FILE);
		File targetFile = new File(TARGET_FILE+"aa.jpg");
		try {
			FileUtil.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
//	public void testDel() {
//		try {
//			FileUtil.del(TEMP_IMAGE_FILE);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testDelFile() {
//		String filepath = TEMP_IMAGE_FILE+"aa.jpg";
//		try {
//			FileUtil.delFile(filepath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testGetImgByHttp() {
		String httpUrl = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
		try {
			FileUtil.getImgByHttp(httpUrl, TEMP_IMAGE_FILE,"aa.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
