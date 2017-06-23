package util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.SystemHelper;

public class TestSystemHelper {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//@Test
//	public void testGetSystemLocalIp() {
//		try {
//			System.out.println(SystemHelper.getSystemLocalIp().getHostAddress());
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testGetSystemOSName() {
		System.out.println(SystemHelper.getSystemOSName());
	}


	@Test
	public void testGetRAMinfo() {
		System.out.println(SystemHelper.getRAMinfo());
	}

}
