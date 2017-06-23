package util.num;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.num.PriceUtil;

public class TestPriceUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCaculate() {

		String str = "1*2+3/4+5-6";
		System.out.println(PriceUtil.caculate(str));
	}

	@Test
	public void testParse() {
		String str = "1*(2+3)/4+5-6";
		System.out.println(PriceUtil.parse(str));
	}

}
