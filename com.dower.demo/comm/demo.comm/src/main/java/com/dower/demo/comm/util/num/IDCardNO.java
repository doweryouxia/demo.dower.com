package com.dower.demo.comm.util.num;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成身份证号
 * @author NiuXueJun
 * 2015-8-6 下午4:46:20
 */
public class IDCardNO {

	 

	/**
	 * 随机生成
	 * 18位身份证号码各位的含义:
	 * 1-2位省、自治区、直辖市代码；
	 * 3-4位地级市、盟、自治州代码；
	 * 5-6位县、县级市、区代码；
	 * 7-14位出生年月日，比如19670401代表1967年4月1日；
	 * 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数；
	 * 18位为校验码，0-9和X。
	 * 作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，
	 * 计算的结果是0-10，如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，
	 * 因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10
	 * @param _province 省、自治区、直辖市代码
	 * @param _city 地级市、盟、自治州代码
	 * @return String 
	 */
	public static String getRandomID(String _province, String _city) {
		String id = "";
		String province;
		String city;
		if (StringUtils.isBlank(_province)) {
			// 随机生成省、自治区、直辖市代码 1-2
			String provinces[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44",
					"45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82" };
			province = provinces[new Random().nextInt(provinces.length - 1)];
		} else {
			province = _province;
		}
		if (StringUtils.isBlank(_city)) {
			// 随机生成地级市、盟、自治州代码 3-4
			String citys[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "21", "22", "23", "24", "25", "26", "27", "28" };
			city = citys[new Random().nextInt(citys.length - 1)];
		} else {
			city = _city;
		}
		// 随机生成县、县级市、区代码 5-6
		String countys[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31", "32", "33", "34", "35", "36", "37", "38" };
		String county = countys[new Random().nextInt(countys.length - 1)];
		// 随机生成出生年月 7-14
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - new Random().nextInt(365 * 43) - (365 * 18));
		String birth = dft.format(date.getTime());
		// 随机生成顺序号 15-17
		// String no = new Random().nextInt(999) + "";
		// String no = String.valueOf(1000 + new
		// Random().nextInt(999)).substring(1);
		// 15-16
		int number;
		number = (int) (Math.random() * (99 - 1) + 1);
		String Sequence = null;
		if (number < 10) {
			// 转换成字符串
			Sequence = Integer.toString(number);
			Sequence = "0" + number;
		} else {
			Sequence = Integer.toString(number);
		}

		// 第17 位 一位性别：0-9
		String sexStr = null;
		int sex;
		sex = (int) (Math.random() * (9 - 0) + 0);
		sexStr = Integer.toString(sex);

		// 随机生成校验码 18
		String checks[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X" };
		String check = checks[new Random().nextInt(checks.length - 1)];
		// 拼接身份证号码
		id = province + city + county + birth + Sequence + sexStr + check;

		return id;
	}

}
