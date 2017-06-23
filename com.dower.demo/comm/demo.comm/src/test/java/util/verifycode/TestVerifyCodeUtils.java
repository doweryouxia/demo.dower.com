package util.verifycode;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.verifycode.VerifyCodeUtils;

public class TestVerifyCodeUtils {

	private static String VERIFYCODE_FILE ="";
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestVerifyCodeUtils.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		properties.load(in);
		VERIFYCODE_FILE = properties.getProperty("verifycode.file");
		File file = new File(VERIFYCODE_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGenerateVerifyCodeInt() {
		System.out.println(VerifyCodeUtils.generateVerifyCode(6));
	}

	@Test
	public void testOutputVerifyImageIntIntStringInt() {
		try {
			VerifyCodeUtils.outputVerifyImage(120, 50, VERIFYCODE_FILE, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testOutputImageIntIntFileString() {
		
		String code = VerifyCodeUtils.generateVerifyCode(5);
		File file = new File(VERIFYCODE_FILE,code+".jpg");
		try {
			VerifyCodeUtils.outputImage(100, 50, file, code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
