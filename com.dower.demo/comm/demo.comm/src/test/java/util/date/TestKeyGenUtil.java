package util.date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.date.KeyGenUtil;

public class TestKeyGenUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetCurrDateTimeString() {
		String format = "yyyy-MM-dd hh:mm:ss";
		System.out.println(KeyGenUtil.getCurrDateTimeString(format));
	}

	@Test
	public void testGenKey() {
		System.out.println(KeyGenUtil.genKey());
		System.out.println(KeyGenUtil.genKey());
		System.out.println(KeyGenUtil.genKey());
		System.out.println(KeyGenUtil.genKey());
	}

}
