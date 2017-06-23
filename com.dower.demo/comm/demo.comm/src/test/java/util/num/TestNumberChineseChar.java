package util.num;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.num.NumberChineseChar;

public class TestNumberChineseChar {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testToChineseCharI() {
		System.out.println(NumberChineseChar.toChineseCharI("1234"));
	}

	@Test
	public void testToChineseCharD() {
		System.out.println(NumberChineseChar.toChineseCharD("567890"));
	}

	@Test
	public void testToChineseChar() {
		System.out.println(NumberChineseChar.toChineseChar("1234.567890"));
	}

}
