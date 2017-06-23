package util.urlcode;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.urlcode.CodingUtils;

public class TestCodingUtils {
	
	private static String urlStr = "http://localhost:8080/ihandy_yanbao_web";

	private static String encodeStr = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testEncodeBase64URL() {
		try {
			encodeStr = CodingUtils.encodeBase64URL(urlStr);
			System.out.println(encodeStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDecodeBase64URL() {
		try {
			System.out.println(CodingUtils.decodeBase64URL(encodeStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEncodeURL() {
		try {
			System.out.println(CodingUtils.encodeURL(urlStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDecodeURL() {
		try {
			System.out.println(CodingUtils.decodeURL(urlStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
