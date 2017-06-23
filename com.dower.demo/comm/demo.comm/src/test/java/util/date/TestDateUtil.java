package util.date;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.date.DateUtil;

public class TestDateUtil {

	private static String startTime = "2015-08-01 10:02:45";
	private static String endTime = "2015-08-04 11:02:55";
	private static String nowTime = "2015-08-04 14:58:27";
	private static Date startDate = new Date(2015, 8, 4);
	
	private static Date endDate = new Date(2015, 8, 4);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateDateIntIntIntIntIntIntInt() {
		System.out.println(DateUtil.createDate(2015, 8, 4, 14, 50, 55, 50));
	}

	@Test
	public void testCreateDateIntIntInt() {
		System.out.println(DateUtil.createDate(2015, 8, 4));
	}

	@Test
	public void testCreateDateIntIntIntInt() {
		System.out.println(DateUtil.createDate(2015, 8, 4, 14));
	}

	@Test
	public void testCreateDateIntIntIntIntInt() {
		System.out.println(DateUtil.createDate(2015, 8, 4, 14,54));
	}

	@Test
	public void testCreateDateIntIntIntIntIntInt() {
		System.out.println(DateUtil.createDate(2015, 8, 4, 14,54,29));
	}

	@Test
	public void testIsExchangeStringStringString() {
		System.out.println(DateUtil.isExchange(startTime, nowTime, endTime));
	}

	@Test
	public void testIsExchangeStringString() {
		
		System.out.println(DateUtil.isExchange(startTime,endTime));
	}

	@Test
	public void testIsEqualStringString() {
		System.out.println(DateUtil.isEqual(startTime, nowTime));
	}

	@Test
	public void testIsEqualString() {
		System.out.println(DateUtil.isEqual(startTime));
	}

	@Test
	public void testIsSameTodayDateDate() {
		
		System.out.println(DateUtil.isSameToday(startDate, endDate));
	}

	@Test
	public void testIsSameTodayDate() {
		System.out.println(DateUtil.isSameToday(startDate));
	}

	@Test
	public void testChineseMD() {
		System.out.println(DateUtil.chineseMD());
	}

	@Test
	public void testChineseYMD() {
		System.out.println(DateUtil.chineseYMD());
	}

	@Test
	public void testGetNowDateShort() {
		System.out.println(DateUtil.getNowDateShort());
	}

	@Test
	public void testGetNowStrShort() {
		System.out.println(DateUtil.getNowStrShort());
	}

	@Test
	public void testGetToday() {
		System.out.println(DateUtil.getToday());
	}

	@Test
	public void testGetNowDate() {
		System.out.println(DateUtil.getNowDate());
	}

	@Test
	public void testGetDate() {
		System.out.println(DateUtil.getDate("20150804151420"));
	}

	@Test
	public void testGetNowStrDate() {
		System.out.println(DateUtil.getNowStrDate());
	}

	@Test
	public void testGetNowYMD() {
		System.out.println(DateUtil.getNowYMD());
	}

	@Test
	public void testGetNowYDate() {
		System.out.println(DateUtil.getNowYDate());
	}

	@Test
	public void testGetNowHour() {
		System.out.println(DateUtil.getNowHour());
	}

	@Test
	public void testGetNowDayOfMonth() {
		System.out.println(DateUtil.getNowDayOfMonth());
	}

	@Test
	public void testGetNowMonth() {
		System.out.println(DateUtil.getNowMonth());
	}

	@Test
	public void testGetNowYear() {
		System.out.println(DateUtil.getNowYear());
	}

	@Test
	public void testGetNowWeek() {
		System.out.println(DateUtil.getNowWeek());
	}

	@Test
	public void testGetDateBeforeMonth() {
		System.out.println(DateUtil.getDateBeforeMonth(1));
	}

	@Test
	public void testGetFormatDate() {
		System.out.println(DateUtil.getFormatDate(nowTime, "yyyy-MM-dd"));
	}

	@Test
	public void testGetFormatDateTime() {
		System.out.println(DateUtil.getFormatDateTime(new Date(), "yyyy-MM-dd hh:mm:ss"));
	}

	@Test
	public void testGetFormatDay() {
		System.out.println(DateUtil.getFormatDay(new Date()));
	}

	@Test
	public void testGetFormatDayWithyyyyMMdd() {
		System.out.println(DateUtil.getFormatDayWithyyyyMMdd(new Date()));
	}

	@Test
	public void testGetDateBefore() {
		System.out.println(DateUtil.getDateBefore(new Date(), 1));
	}

	@Test
	public void testGetDateAfter() {
		System.out.println(DateUtil.getDateAfter(new Date(), 1));
	}

	@Test
	public void testGetDateInt() {
		System.out.println(DateUtil.getDateInt());
	}

	@Test
	public void testXmlDate2Date() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		XMLGregorianCalendar xcal = null;
		try {
			xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(DateUtil.xmlDate2Date(xcal));
	}

	@Test
	public void testTruncateTimeOfDate() {
		System.out.println(DateUtil.truncateTimeOfDate(new Date()));
	}

	@Test
	public void testDaysBetweenDateDate() {
		try {
			System.out.println(DateUtil.daysBetween(startDate, endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDaysBetweenStringString() {
		try {
			System.out.println(DateUtil.daysBetween(startTime, endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDateFormat() {
		try {
			System.out.println(DateUtil.dateFormat(nowTime, "yyyy-MM-dd hh:mm:ss", "yyyy年MM月dd日  hh时mm分ss秒"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMonthsBetween() {
		System.out.println(DateUtil.monthsBetween(startDate, endDate));
	}



	@Test
	public void testSecondsBetween(){
		try {
			System.out.println(DateUtil.secondsBetween(DateUtil.getDate("20150929164030"), DateUtil.getNowDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
