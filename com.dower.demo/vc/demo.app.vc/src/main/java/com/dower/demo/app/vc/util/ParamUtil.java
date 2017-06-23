package com.dower.demo.app.vc.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author NiuXueJun
 *
 */
public class ParamUtil {

	public static String ENCODING_DEFAULT = "UTF-8";

	public static String GET_ENCODING_DEFAULT = "UTF-8";

	public static String FILE_WRITING_ENCODING = "GBK";

	public static int getIntParamter(HttpServletRequest _request, String _param, int _defaultValue) {
		int dv = _defaultValue;
		int rdv = 0;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn)) {
			return dv;
		}
		try {
			rdv = Integer.parseInt(strIn);
		} catch (Exception e) {
			rdv = dv;
		}

		return rdv;
	}

	public static int getIntAttribute(HttpServletRequest _request, String _param, int _defaultValue) {
		int dv = _defaultValue;
		int rdv = 0;
		Integer iIn = (Integer) _request.getAttribute(_param);
		if (iIn == null) {
			return dv;
		}
		try {
			rdv = iIn.intValue();
		} catch (Exception e) {
			rdv = dv;
		}

		return rdv;
	}

	public static long getLongAttribute(HttpServletRequest _request, String _param, long _defaultValue) {
		long dv = _defaultValue;
		long rdv = 0L;
		Long lIn = (Long) _request.getAttribute(_param);
		if (lIn == null) {
			return dv;
		}
		try {
			rdv = lIn.longValue();
		} catch (Exception e) {
			rdv = dv;
		}

		return rdv;
	}

	public static long getLongParamter(HttpServletRequest _request, String _param, long _defaultValue) {
		long dv = _defaultValue;
		long rdv = 0L;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn)) {
			return dv;
		}
		try {
			rdv = Long.parseLong(strIn);
		} catch (Exception e) {
			rdv = dv;
		}

		return rdv;
	}

	public static String getStr(HttpServletRequest _request, String _param, String _defaultValue) {
		String dv = _defaultValue;

		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn)) {
			return dv;
		}

		return getStr(strIn).trim();
	}

	public static String getStr(HttpServletRequest _request, String _param, String _defaultValue, String _encoding) {
		String dv = _defaultValue;

		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn)) {
			return dv;
		}

		return getStr(strIn, _encoding);
	}

	public static boolean isEmpty(String _string) {
		return (_string == null) || (_string.trim().length() == 0);
	}

	public static boolean isEmptyStr(String _string) {
		return (_string == null) || (_string.trim().length() == 0);
	}

	public static String showObjNull(Object p_sValue) {
		return showObjNull(p_sValue, "");
	}

	public static String showObjNull(Object _sValue, String _sReplaceIfNull) {
		if (_sValue == null) {
			return _sReplaceIfNull;
		}
		return _sValue.toString();
	}

	public static String showNull(String p_sValue) {
		return showNull(p_sValue, "");
	}

	public static String showNull(String _sValue, String _sReplaceIfNull) {
		return _sValue != null ? _sValue : _sReplaceIfNull;
	}

	public static String expandStr(String _string, int _length, char _chrFill, boolean _bFillOnLeft) {
		int nLen = _string.length();
		if (_length <= nLen)
			return _string;
		String sRet = _string;
		for (int i = 0; i < _length - nLen; i++) {
			sRet = sRet + _chrFill;
		}
		return sRet;
	}

	public static String setStrEndWith(String _string, char _chrEnd) {
		if (_string == null)
			return null;
		if (_string.charAt(_string.length() - 1) != _chrEnd) {
			return _string + _chrEnd;
		}
		return _string;
	}


	public static String getStr(String _strSrc) {
		return getStr(_strSrc, null);
	}

	public static String getStr(String _strSrc, boolean _bPostMethod) {
		return getStr(_strSrc, _bPostMethod ? ENCODING_DEFAULT : GET_ENCODING_DEFAULT);
	}

	public static String getStr(String _strSrc, String _encoding) {
		if ((_encoding == null) || (_encoding.length() == 0))
			return _strSrc;
		try {
			byte[] byteStr = new byte[_strSrc.length()];
			char[] charStr = _strSrc.toCharArray();
			for (int i = byteStr.length - 1; i >= 0; i--) {
				byteStr[i] = (byte) charStr[i];
			}
			return new String(byteStr, _encoding);
		} catch (Exception ex) {
		}
		return _strSrc;
	}

	public static String toISO_8859(String _strSrc) {
		if (_strSrc == null)
			return null;
		try {
			return new String(_strSrc.getBytes(), "ISO-8859-1");
		} catch (Exception ex) {
		}
		return _strSrc;
	}

	public static String toUnicode(String _strSrc) {
		return toISO_8859(_strSrc);
	}

	public static byte[] getUTF8Bytes(String _string) {
		char[] c = _string.toCharArray();
		int len = c.length;
		int count = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127)
				count++;
			else if (ch <= 2047)
				count += 2;
			else {
				count += 3;
			}
		}
		byte[] b = new byte[count];
		int off = 0;
		for (int i = 0; i < len; i++) {
			int ch = c[i];
			if (ch <= 127) {
				b[(off++)] = (byte) ch;
			} else if (ch <= 2047) {
				b[(off++)] = (byte) (ch >> 6 | 0xC0);
				b[(off++)] = (byte) (ch & 0x3F | 0x80);
			} else {
				b[(off++)] = (byte) (ch >> 12 | 0xE0);
				b[(off++)] = (byte) (ch >> 6 & 0x3F | 0x80);
				b[(off++)] = (byte) (ch & 0x3F | 0x80);
			}
		}

		return b;
	}


	public static String byteToString(byte[] _bytes, char _delim, int _radix) {
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++) {
			if (i > 0)
				sRet = sRet + _delim;
			sRet = sRet + Integer.toString(_bytes[i], _radix);
		}

		return sRet;
	}

	public static String filterForXML(String _sContent) {
		if (_sContent == null)
			return "";
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&':
				retBuff.append("&amp;");
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			case '\'':
				retBuff.append("&apos;");
				break;
			default:
				retBuff.append(cTemp);
			}

		}

		return retBuff.toString();
	}

	public static String filterForHTMLValue(String _sContent) {
		if (_sContent == null)
			return "";
		char[] srcBuff = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int) (nLen * 1.8D));
		for (int i = 0; i < nLen; i++) {
			char cTemp = srcBuff[i];
			switch (cTemp) {
			case '&':
				if (i + 1 < nLen) {
					cTemp = srcBuff[(i + 1)];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else {
					retBuff.append("&amp;");
				}
				break;
			case '<':
				retBuff.append("&lt;");
				break;
			case '>':
				retBuff.append("&gt;");
				break;
			case '"':
				retBuff.append("&quot;");
				break;
			default:
				retBuff.append(cTemp);
			}

		}

		return retBuff.toString();
	}

	public static final boolean isChineseChar(int c) {
		return c > 127;
	}

	public static int getBytesLength(String _string) {
		if (_string == null)
			return 0;
		char[] srcBuff = _string.toCharArray();
		int nGet = 0;
		for (int i = 0; i < srcBuff.length; i++) {
			char aChar = srcBuff[i];
			nGet += (aChar > '' ? 2 : 1);
		}

		return nGet;
	}

	public static String URLEncode(String s) {
		try {
			return URLEncoder.encode(s, GET_ENCODING_DEFAULT);
		} catch (Exception ex) {
		}
		return s;
	}

	public boolean isChinese(char c) {
		return Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
	}

	public static char byteToChar(byte[] b) {
		int s = 0;
		if (b[0] > 0)
			s += b[0];
		else
			s += 256 + b[0];
		s *= 256;
		if (b[1] > 0)
			s += b[1];
		else
			s += 256 + b[1];
		char ch = (char) s;
		return ch;
	}

	public static String formatDate(Date d1, String f1) {
		if (d1 == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(f1);
		return sdf.format(d1);
	}

	public static Date parseDate(long dateTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTime);
		return c.getTime();
	}

	public static Date parseDate(String d1, String f1) {
		if ((d1 == null) || (d1.trim().equals("")))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(f1);
		Date d2 = null;
		try {
			d2 = sdf.parse(d1);
		} catch (Exception e) {
			System.out.println(d1 + "日期格式错误" + e);
		}
		return d2;
	}

	public static String formatDateByNow(String f1) {
		return formatDate(new Date(), f1);
	}

	public static String cutString(String title, int num) {
		if (title == null)
			return "";
		if (title.length() > num)
			return title.substring(0, num) + "...";
		return title;
	}

	public static String filterHtml(String strIn) {
		if ((strIn == null) || (strIn.trim().length() == 0)) {
			return strIn;
		}
		String regEx_html = "</?[^<]*>";
		return strIn.replaceAll(regEx_html, "");
	}

	public static void main(String[] args) {
		System.out.println(filterHtml("<b>feww<be></b>dsf<be></be>"));
	}
}
