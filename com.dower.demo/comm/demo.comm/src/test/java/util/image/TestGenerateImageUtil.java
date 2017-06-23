package util.image;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.image.GenerateImageUtil;

public class TestGenerateImageUtil {

	private static String imgStr = null;
	
	private static String TEMP_IMAGE_FILE = "";
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestGenerateImageUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties  = new Properties();
		properties.load(in);
		TEMP_IMAGE_FILE = properties.getProperty("temp.image.file");
		File file = new File(TEMP_IMAGE_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetImageStr() {
		String imgFilePath = TEMP_IMAGE_FILE+"aa.jpg";
		imgStr = GenerateImageUtil.getImageStr(imgFilePath);
		System.out.println(imgStr);
	}

	@Test
	public void testGenerateImage() {
		String imgFilePath = TEMP_IMAGE_FILE+"bb.jpg";
		GenerateImageUtil.generateImage(imgStr, imgFilePath);
	}
	
	
}
