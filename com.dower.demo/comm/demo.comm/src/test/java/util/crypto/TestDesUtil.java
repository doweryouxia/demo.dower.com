package util.crypto;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.crypto.DesUtil;

public class TestDesUtil {

	private static String data = "1408456553255,niuxuejun@ihandy.cn";
	private static String key1 = DesUtil.key;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testReplaceBlank() {
		String str = "aaa   bbb   ccc ";
		System.out.println("testReplaceBlank:"+DesUtil.replaceBlank(str));
	}

	@Test
	public void testEncrypt() {
		
		try {
			System.err.println("testEncrypt:"+DesUtil.replaceBlank(DesUtil.encrypt(data, key1)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testEncryptSimple() {
		try {
			System.out.println("testEncryptSimple:"+DesUtil.encryptSimple(data, key1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDecrypt() {
		try {
			System.err.println("testDecrypt:"+DesUtil.decrypt(DesUtil.encrypt(data, key1), key1).trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
