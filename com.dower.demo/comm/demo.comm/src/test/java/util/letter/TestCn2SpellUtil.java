package util.letter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.letter.Cn2SpellUtil;


public class TestCn2SpellUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testConverterToFirstSpell() {
		System.out.println(Cn2SpellUtil.converterToFirstSpell("你好我是中国人"));
	}

	@Test
	public void testConverterToSpell() {
		System.out.println(Cn2SpellUtil.converterToSpell("你好我是中国人"));
	}

}
