package util.html;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.html.HtmlOutputUtil;

public class TestHtmlOutputUtil {
	
	private HtmlOutputUtil htmlOutputUtil = new HtmlOutputUtil();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetContent() {
		htmlOutputUtil.setContent("<p>你好的萨芬撒就库萨克，撒地方就是开发商说：“按时发顺丰萨芬”</p>");
		System.out.println(htmlOutputUtil.getContent());
	}

	@Test
	public void testGetSubContent() {
		htmlOutputUtil.setContent("<p>你好的萨芬撒就库萨克，撒地方就是开发商说：“按时发顺丰萨芬速度飞萨芬撒发生的发生的发生大发生的发生大发生的发生的发生的发生的发生地方地方撒的发生的发生的发生的发生地方发生大幅</r>度撒发生大法师答复时代发生大幅萨芬撒发生大撒地方士大夫士大夫撒士大夫撒的方式大”</p>");
		System.out.println(htmlOutputUtil.getSubContent());
	}

}
