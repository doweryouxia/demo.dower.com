package util.mail;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.mail.SendMailUtil;

public class TestSendMailUtil {

	private static String sendServer = "mail.ihandy.cn";
	private static String send = "yanbao@ihandy.cn";
	private static String pwd = "yanbaowxtl11";
	private static String receive = "niuxuejun@163.com";
	
	private static String TEMP_IMAGE_FILE = "";
	
	private static Properties properties;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InputStream in = TestSendMailUtil.class.getClassLoader().getResourceAsStream("test.properties");
		properties  = new Properties();
		properties.load(in);
		TEMP_IMAGE_FILE = properties.getProperty("temp.image.file");
		File file = new File(TEMP_IMAGE_FILE);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSendMail() {
		SendMailUtil.sendMail("测试邮件！", "test", sendServer, send, pwd, receive);
	}

	@Test
	public void testSendMailMu(){
//		List relist=new ArrayList();
//		relist.add("zhujiajia@ihandy.cn");
//		relist.add("105534792@qq.com");
//		SendMailUtil.sendMailMu("测试邮件","标题",sendServer, send, pwd,relist);
	}

	@Test
	public void testSendHtmlMail() {
		SendMailUtil.sendHtmlMail(sendServer, send, receive, "测试发送html标题", "<font size='18' color='red'>测试内容</font>", pwd);
	}

	@Test
	public void testSendAdjunctMessage() {
		SendMailUtil.sendAdjunctMessage(sendServer, send, receive, "测试发送带附件标题", "测试发送带附件内容", pwd, TEMP_IMAGE_FILE+"aa.jpg");
	}

}
