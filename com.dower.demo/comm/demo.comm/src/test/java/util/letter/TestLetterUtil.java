package util.letter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.letter.LetterUtil;

public class TestLetterUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCn2py() {
		System.out.println(LetterUtil.cn2py("重庆重视发展IT行业，大多数外企，如，IBM等进驻山城"));
	}

}
