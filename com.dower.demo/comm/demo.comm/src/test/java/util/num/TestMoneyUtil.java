package util.num;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.num.MoneyUtil;

public class TestMoneyUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testToChinese() {
		System.out.println(MoneyUtil.toChinese("199999.21356"));
	}

}
