package util.num;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.num.IDCardNO;

public class TestIDCardNO {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetRandomID() {
		System.out.println(IDCardNO.getRandomID("14", "04"));
	}

}
