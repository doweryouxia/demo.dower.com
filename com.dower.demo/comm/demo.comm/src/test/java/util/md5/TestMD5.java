package util.md5;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.md5.MD5;


public class TestMD5 {

	private static String KEY_FILE ="";
	
	private static Properties properties;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestMD5.class.getClassLoader().getResourceAsStream("test.properties");
		properties = new Properties();
		properties.load(in);
		KEY_FILE = properties.getProperty("key.file");
		File file = new File(KEY_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testEncode() {
		System.out.println(MD5.encode("123"));
	}

	@Test
	public void testCompileString() {
		System.out.println(MD5.compile("123"));
	}

	@Test
	public void testMD5Encode() {
		byte[] toencode = new byte[]{'1','2','3'};
		String md5Byte = MD5.MD5Encode(toencode);
		System.out.println(md5Byte);
	}

	@Test
	public void testMD5EncodeByte() {
		byte[] toencode = new byte[]{'1','2','3'};
		byte[] md5Byte = MD5.MD5EncodeByte(toencode);
		System.out.println(md5Byte.length);
		for (byte b : md5Byte) {
			System.out.print(b+" ");
		}
		System.out.println();
	}

	@Test
	public void testCompileFile() {
		try {
			System.out.println ( MD5.compile(new File(KEY_FILE+"key.txt")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
