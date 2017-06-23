package com.dower.demo.app.vc.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @author NiuXueJun
 * 2015-7-31 下午2:39:22
 */
public class DateUtil {
	
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	public static String YEARMMDD_FORMAT = "yyyy-MM-dd";
	public static String YEARMMDD_HHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String YEARMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";
	public static String YEARMMDD_DATE_FORMAT = "yyyyMMdd";
	public static String HHMMSS_FORMAT = "HH:mm:ss";
	public static String CHINA_YEARMMDD = "yyyy年MM月dd日";
	public static SimpleDateFormat YEARMMDD_HHMMSS = new SimpleDateFormat(YEARMMDD_HHMMSS_FORMAT);
	public static SimpleDateFormat YEARMMDD = new SimpleDateFormat(YEARMMDD_FORMAT);
	public static SimpleDateFormat YEARMMDDHHMMSS = new SimpleDateFormat(YEARMMDDHHMMSS_FORMAT);
	public static SimpleDateFormat YEARMMDD_YMD = new SimpleDateFormat(CHINA_YEARMMDD);
	public static SimpleDateFormat YEARMMDDATA = new SimpleDateFormat(YEARMMDD_DATE_FORMAT);

	public static SimpleDateFormat CHINESE_FORMAT = new SimpleDateFormat("MM月dd日 kk点mm分");
	public static SimpleDateFormat CHINESE_FORMAT_YEAR = new SimpleDateFormat("yyyy年MM月dd日 kk点mm分");
	public static final Calendar CALENDAR = Calendar.getInstance();

	/**
	 * 根据年、月、日、时、分、秒、毫秒生成日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 小时
	 * @param minute 分
	 * @param second 秒
	 * @param millisecond 毫秒
	 * @return Date
	 */
	public static synchronized Date createDate(final int year, final int month, final int day, final int hour, final int minute, final int second,
			final int millisecond) {
		CALENDAR.clear();
		CALENDAR.set(year, month - 1, day, hour, minute, second);
		CALENDAR.set(Calendar.MILLISECOND, millisecond);
		return CALENDAR.getTime();
	}

	/**
	 * 根据年、月、日生成日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return Date
	 */
	public static synchronized Date createDate(final int year, final int month, final int day) {
		createDate(year, month, day, 0, 0, 0, 0);
		return CALENDAR.getTime();
	}

	/**
	 * 根据年、月、日、时生成日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 小时
	 * @return Date
	 */
	public static synchronized Date createDate(final int year, final int month, final int day, final int hour) {
		createDate(year, month, day, hour, 0, 0, 0);
		return CALENDAR.getTime();
	}

	/**
	 * 根据年、月、日、时、分生成日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 小时
	 * @param minute 分
	 * @return Date
	 */
	public static synchronized Date createDate(final int year, final int month, final int day, final int hour, final int minute) {
		createDate(year, month, day, hour, minute, 0, 0);
		return CALENDAR.getTime();
	}

	/**
	 * 根据年、月、日、时、分、秒生成日期
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 小时
	 * @param minute 分
	 * @param second 秒
	 * @return Date
	 */
	public static synchronized Date createDate(final int year, final int month, final int day, final int hour, final int minute, final int second) {
		createDate(year, month, day, hour, minute, second, 0);
		return CALENDAR.getTime();
	}

	/**
	 * 判断是否在两个时间段之间 比较时间格式 yyyy-MM-dd HH:mm:ss
	 * @param startTime
	 *            开始时间
	 * @param nowTime
	 *            比较时间
	 * @param endTime
	 *            结束时间
	 * @return boolean
	 */
	public static boolean isExchange(String startTime, String nowTime, String endTime) {
		if (startTime == null || endTime == null)
			return false;
		Date startDate = null;
		Date endDate = null;
		Date nowDate = null;
		try {
			startDate = YEARMMDD_HHMMSS.parse(startTime);
			endDate = YEARMMDD_HHMMSS.parse(endTime);
			if (nowTime == null || "".equals(nowTime)) {
				nowDate = getNowDate();
			} else {
				nowDate = YEARMMDD_HHMMSS.parse(nowTime);
			}
		} catch (Exception e) {
		}
		return nowDate.after(startDate) && nowDate.before(endDate);
	}

	/**
	 * 判断当前时间在两个时间之内 与当前时间相比
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return boolean
	 */
	public static boolean isExchange(String startTime, String endTime) {
		return isExchange(startTime, null, endTime);
	}

	/**
	 * 判断时间是否是当天
	 * 
	 * @param startTime
	 * @return boolean
	 */
	public static boolean isEqual(String startTime, String nowTime) {
		Date startDate = null;
		Date nowDate = null;
		try {
			startDate = YEARMMDD.parse(startTime);
			if (nowTime == null || "".equals(nowTime)) {
				nowDate = YEARMMDD.parse(getNowStrDate());
			} else {
				nowDate = YEARMMDD.parse(nowTime);
			}
		} catch (Exception e) {
		}
		return startDate.compareTo(nowDate) == 0 ? true : false;
	}

	/**
	 * 判断日期是否是当天
	 * @param startTime
	 * @return boolean
	 */
	public static boolean isEqual(String startTime) {
		return isEqual(startTime, null);
	}

	/**
	 * 判断是否是同一天
	 * 
	 * @param startTime
	 *            yyyy-MM-dd
	 * @param endTime
	 *            yyyy-MM-dd
	 * @return boolean
	 */
	public static boolean isSameToday(Date startTime, Date endTime) {
		if (startTime == null)
			return false;
		try {
			String startTimeStr = YEARMMDD.format(startTime);
			String endTimeStr = "";
			if (endTime == null) {
				endTimeStr = getNowStrShort();
			} else {
				endTimeStr = YEARMMDD.format(endTime);
			}
			return startTimeStr.equals(endTimeStr) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 判断是否是同一天
	 * @param startTime
	 * @return boolean
	 */
	public static boolean isSameToday(Date startTime) {
		return isSameToday(startTime, null);
	}
	
	/**
	 * 转换字符串日期格式
	 * @return MM月dd日 kk点mm分
	 */
	public static String chineseMD() {
		Date now = new Date();
		return CHINESE_FORMAT.format(now);
	}

	/**
	 * 转换字符串日期格式
	 * @return yyyy年MM月dd日 kk点mm分
	 */
	public static String chineseYMD() {
		Date now = new Date();
		return CHINESE_FORMAT_YEAR.format(now);
	}

	/**
	 * 获取当前日期
	 * @return yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		String dateString = YEARMMDD.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = YEARMMDD.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间格式 yyyy-MM-dd
	 */
	public static String getNowStrShort() {
		Date currentTime = new Date();
		String dateString = YEARMMDD.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当日
	 * 
	 * @return 返回时间格式yyyyMMdd
	 */
	public static String getToday() {
		Date currentTime = new Date();
		String dateString = YEARMMDDATA.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 获取现在时间Timestamp类型
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Timestamp getNowTimestamp() {
		return  new Timestamp(System.currentTimeMillis()) ;
	}

	/**
	 * 字符串转换成日期
	 * @param timeStr 日期格式：yyyyMMddHHmmss
	 * @return Date
	 */
	public static Date getDate(String timeStr) {
		try {
			return YEARMMDDHHMMSS.parse(timeStr);
		} catch (Exception e) {
		}
		return new Date();
	}



	/**
	 * 获取当前时间
	 * 
	 * @return 返回短时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowStrDate() {
		Date currentTime = new Date();
		String dateString = YEARMMDD_HHMMSS.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return 返回短时间格式 yyyy年MM月dd日
	 */
	public static String getNowYMD() {
		Date currentTime = new Date();
		String dateString = YEARMMDD_YMD.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 返回短时间格式 yyyyMMddHHmmss
	 */
	public static String getNowYDate() {
		Date currentTime = new Date();
		String dateString = YEARMMDDHHMMSS.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前的小时
	 * 
	 * @return int
	 */
	public static int getNowHour() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
		return hour;
	}

	/**
	 * 获取当前的月份中的天数
	 * 
	 * @return int
	 */
	public static int getNowDayOfMonth() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.DAY_OF_MONTH);// 小时
		return hour;
	}

	/**
	 * 获取当前的月份
	 * 
	 * @return int
	 */
	public static int getNowMonth() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.MONTH);// 月份
		return hour;
	}

	/**
	 * 获取当前的年份
	 * 
	 * @return int
	 */
	public static int getNowYear() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.YEAR);// 年份
		return hour;
	}

	/**
	 * 获取当前的年的第几周
	 * 
	 * @return int
	 */
	public static int getNowWeek() {
		Calendar ca = Calendar.getInstance();
		int week = ca.get(Calendar.WEEK_OF_YEAR);// 年中第几周
		return week;
	}

	/**
	 * 得到本日的前几个月时间 如果number=2当日为2007-9-1,那么获得2007-7-1
	 * @param number
	 * @return Date
	 */
	public static Date getDateBeforeMonth(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -number);
		return cal.getTime();
	}

	/**
	 * 根据格式得到格式化后的日期
	 * 
	 * @param currDate
	 *            要格式化的日期
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的日期，格式由参数<code>format</code>
	 *         定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(YEARMMDD_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDateTime(java.util.Date currDate, String format) {
		if (currDate == null) {
			return "";
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(YEARMMDD_HHMMSS_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return "";
	}

	/**
	 * 得到格式化后的日，格式为yyyyMMdd，如20060210
	 * 
	 * @param currDate  要格式化的日期
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
	 */
	public static String getFormatDay(java.util.Date currDate) {
		return getFormatDateTime(currDate, YEARMMDD_DATE_FORMAT);
	}

	/**
	 * 返回年月日格式的日期
	 * 
	 * @param currDate
	 * @see #getFormatDateTime(java.util.Date,String)
	 * @return String yyyy-MM-dd
	 */
	public static String getFormatDayWithyyyyMMdd(java.util.Date currDate) {
		return getFormatDateTime(currDate, YEARMMDD_FORMAT);
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d 
	 * @param day
	 * @return Date
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 获得当前日期的整数型
	 * 
	 * @return int
	 */
	public static int getDateInt() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sf.format(date));
	}

	/**
	 * 查询车险保单专用 将XMLGregorianCalendar转换为Date然后格式化输出
	 * 
	 * @param cal
	 * @return date
	 */
	public static String xmlDate2Date(XMLGregorianCalendar cal) {
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		if (cal != null) {
			date = sdf0.format(cal.toGregorianCalendar().getTime());
		}

		return date;
	}

	/**
	 * 删除给定Date的时分秒毫秒
	 * 
	 * @param now
	 * @return Date
	 */
	public static Date truncateTimeOfDate(Date now) {
		if (now == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 日期格式相互转换
	 * 
	 * @param date
	 *            字符串日期
	 * @param fFormat
	 *            目前的日期格式
	 * @param tFormat
	 *            要转换成的格式
	 * @return 返回转换后的日期
	 * @throws ParseException
	 */
	public static String dateFormat(String date, String fFormat, String tFormat) throws ParseException {
		SimpleDateFormat fd = new SimpleDateFormat(fFormat);
		SimpleDateFormat td = new SimpleDateFormat(tFormat);
		Date d = fd.parse(date);
		return td.format(d);
	}

	/**
	 * 计算两个日期相差的月份
	 * 
	 * @param sd
	 *            开始日期
	 * @param bd
	 *            之后日期
	 * @return int
	 */
	public static int monthsBetween(Date sd, Date bd) {
		Calendar c = Calendar.getInstance();
		c.setTime(sd);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		c.setTime(bd);
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH);
		int result = 0;
		if (year == year1) {
			result = month1 - month;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (year1 - year) + month1 - month;// 两个日期相差几个月，即月份差
		}
		return result;
	}
	
	/**
	 * 计算两个日期相差的月份(超过1天就算1个月)
	 * 
	 * @param sd
	 *            开始日期
	 * @param bd
	 *            之后日期
	 * @return int
	 */
	public static int monthsBetweenRound(Date sd, Date bd) {
		try{
		Calendar c = Calendar.getInstance();
		c.setTime(sd);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(bd);
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH);
		int day1 = c.get(Calendar.DAY_OF_MONTH);
		int result = 0;
		if (year == year1) {
			result = month1 - month;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (year1 - year) + month1 - month;// 两个日期相差几个月，即月份差
		}
		
		if(day<day1){
			result ++;
		}
		
		if(result == 0){
			result ++;
		}
		return result;
		}catch(Exception e){
			log.error("计算两个日期相差的月份 错误,参数开始日期="+sd+" ,之后日期="+bd,e);
			return 1;
		}
	}
	
	/**
	 * 获取两个时间相差年份
	 * @param sd
	 * @param bd
	 * @return
	 */
	public static int yearBetween(Date sd, Date bd){
		Calendar c = Calendar.getInstance();
		c.setTime(sd);
		int year = c.get(Calendar.YEAR);
		c.setTime(bd);
		int year1 = c.get(Calendar.YEAR);
		return Math.abs(year - year1);
	}
	
	/**
	 * 获取两个时间相差年份(精确比对年月日)
	 * @param sd
	 * @param bd
	 * @return
	 */
	public static int yearBetweenRound(Date sd, Date bd){
		Calendar c = Calendar.getInstance();
		c.setTime(sd);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(bd);
		int year1 = c.get(Calendar.YEAR);
		int month1 = c.get(Calendar.MONTH);
		int day1 = c.get(Calendar.DAY_OF_MONTH);
		int result = 0;
		if (year == year1) {
			result = 0;
		}else {
			if(month>month1){
				result = year1 - year -1;
			}else if(month == month1 && day>day1){
					result = year1 - year -1;
			}else{
				result = year1 - year;
			}
		}
		
		return result;
	}

	/**
	 * 计算两个日期之间相差的秒数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int secondsBetween(Date smdate, Date bdate) throws ParseException {
		long time1 = smdate.getTime();
		long time2 = bdate.getTime();
		long between_seconds = (time2 - time1) / 1000 ;
		return Integer.parseInt(String.valueOf(between_seconds));
	}

	/**
	 * 根据格式得到时间戳，格式为yyyy-MM-dd HH:mm:ss，如2015-10-12 05:30:10
	 * @param data  需要转换的日期
	 * @return long 时间戳
	 */
	public static long getTimeStemp(String data,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(data);
		} catch (ParseException e) {
		}
		long timeStemp = date.getTime();
		return timeStemp;
	}
	
	/**
	 * 根据格式得到时间戳，格式为yyyy-MM-dd HH:mm:ss，如2015-10-12 05:30:10
	 * @param data  需要转换的日期
	 * @return long 时间戳
	 */
	public static long getTimeStemp(String data) {
		Date date = null;
		try {
			date = YEARMMDD_HHMMSS.parse(data);
		} catch (ParseException e) {
		}
		long timeStemp = date.getTime();
		return timeStemp;
	}

}
