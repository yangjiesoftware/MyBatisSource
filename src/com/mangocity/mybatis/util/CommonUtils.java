package com.mangocity.mybatis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

/**
 * @时间 : 2014-11-3
 * @email : <a href="yangjie_software@163.com|hotmail.com">yangjie</a>
 * @说明 : 常用工具类<其中包括对日期、加减乘除、数据类型转换>
 * @author ：yangjie 详细说明:
 *         isEmpty、isNotEmpty、isBlank、isNotBlank、trim、trimToEmpty、trimToNull
 *         addYears、addMonth、addDay、addWeek format strToDouble、strToBigdecimal
 *         div、add、mul getSystemTime joinUrlParameter generateRandom copyArray
 *         arrayToList getObjectIdentityHashCode
 */
public class CommonUtils {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return true if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Object object) {
		return object == null ? true : false;
	}

	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为空白
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 是否是数字字符串(可正可负)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[-+]?[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否是奇数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isOdd(int num) {
		return (num & 1) != 0;
	}

	/**
	 * 去掉字符串的前后空白字符串
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return if the String is null,return null,else return trim string
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 如果前后空格,再判断该字符串是否为空字符串 如果是空字符串,则返回空
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * 如果字符串是null,则返回"", 否则返回去除前后空格的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static Integer getMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static Integer getYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Integer getDate() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		return date;
	}

	/**
	 * 获得当月的最后一天
	 * 
	 * @return
	 */
	public static Integer getCurrentMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		int date = calendar.get(Calendar.DATE);
		return date;
	}

	/**
	 * 获得当月的最后一天
	 * 
	 * @return Date
	 */
	public static Date getCurrentMonthLastDate() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	/**
	 * Adds a number of years to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * Adds a number of months to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * Adds a number of weeks to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * Adds a number of days to a date returning a new object. The original date
	 * object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * Adds a number of hours to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * Adds a number of minutes to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * Adds a number of seconds to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * Adds a number of milliseconds to a date returning a new object. The
	 * original date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	public static String formatHourMinSec(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String addYears(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.YEAR, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addMonths(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.MONTH, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addWeeks(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.WEEK_OF_YEAR, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addDays(String date, int amount, String parsePatterns)
			throws ParseException {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.DAY_OF_MONTH, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addHours(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.HOUR_OF_DAY, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addMinutes(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.MINUTE, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addSeconds(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.SECOND, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	public static String addMilliseconds(String date, int amount, String parsePatterns) {
		try {
			return CommonUtils.format(
					add(CommonUtils.parseDate(date, parsePatterns), Calendar.MILLISECOND, amount),
					parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误");
		}
	}

	/**
	 * Adds to a date returning a new object. The original date object is
	 * unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to add to
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @deprecated Will become privately scoped in 3.0
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * 转换单个日期模式的日期字符串
	 * 
	 * @param str
	 * @param parsePatterns
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str, String parsePatterns) throws ParseException {
		return parseDate(str, new String[] { parsePatterns });
	}

	/**
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable (or there were
	 *             none)
	 */
	public static Date parseDate(String str, String[] parsePatterns) throws ParseException {
		return parseDateWithLeniency(str, parsePatterns, true);
	}

	/**
	 * <p>
	 * Parses a string representing a date by trying a variety of different
	 * parsers.
	 * </p>
	 * 
	 * <p>
	 * The parse will try each parse pattern in turn. A parse is only deemed
	 * successful if it parses the whole of the input string. If no parse
	 * patterns match, a ParseException is thrown.
	 * </p>
	 * 
	 * @param str
	 *            the date to parse, not null
	 * @param parsePatterns
	 *            the date format patterns to use, see SimpleDateFormat, not
	 *            null
	 * @param lenient
	 *            Specify whether or not date/time parsing is to be lenient.
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *             if the date string or pattern array is null
	 * @throws ParseException
	 *             if none of the date patterns were suitable
	 * @see java.util.Calender#isLenient()
	 */
	private static Date parseDateWithLeniency(String str, String[] parsePatterns, boolean lenient)
			throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException("Date and Patterns must not be null");
		}
		SimpleDateFormat parser = new SimpleDateFormat();
		parser.setLenient(lenient);
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {

			String pattern = parsePatterns[i];
			if (parsePatterns[i].endsWith("ZZ")) {
				pattern = pattern.substring(0, pattern.length() - 1);
			}
			parser.applyPattern(pattern);
			pos.setIndex(0);
			String str2 = str;
			if (parsePatterns[i].endsWith("ZZ")) {
				int signIdx = indexOfSignChars(str2, 0);
				while (signIdx >= 0) {
					str2 = reformatTimezone(str2, signIdx);
					signIdx = indexOfSignChars(str2, ++signIdx);
				}
			}
			Date date = parser.parse(str2, pos);
			if (date != null && pos.getIndex() == str2.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}

	/**
	 * Index of sign charaters (i.e. '+' or '-').
	 * 
	 * @param str
	 *            The string to search
	 * @param startPos
	 *            The start position
	 * @return the index of the first sign character or -1 if not found
	 */
	private static int indexOfSignChars(String str, int startPos) {
		int idx = indexOf(str, '+', startPos);
		if (idx < 0) {
			idx = indexOf(str, '-', startPos);
		}
		return idx;
	}

	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	/**
	 * Reformat the timezone in a date string.
	 * 
	 * @param str
	 *            The input string
	 * @param signIdx
	 *            The index position of the sign characters
	 * @return The reformatted string
	 */
	private static String reformatTimezone(String str, int signIdx) {
		String str2 = str;
		if (signIdx >= 0 && signIdx + 5 < str.length()
				&& Character.isDigit(str.charAt(signIdx + 1))
				&& Character.isDigit(str.charAt(signIdx + 2)) && str.charAt(signIdx + 3) == ':'
				&& Character.isDigit(str.charAt(signIdx + 4))
				&& Character.isDigit(str.charAt(signIdx + 5))) {
			str2 = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
		}
		return str2;
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 * 
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern) {
		if (isBlank(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 格式化Calendar对象
	 * 
	 * @param calendar
	 * @param pattern
	 * @return
	 */
	public static String format(Calendar calendar, String pattern) {
		if (isEmpty(calendar)) {
			throw new IllegalArgumentException("calendar不能为空");
		}
		if (isBlank(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 格式化millis对象
	 * 
	 * @param millis
	 * @param pattern
	 * @return
	 */
	public static String format(long millis, String pattern) {
		if (isBlank(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date(millis));
	}

	/**
	 * 字符串转int
	 * 
	 * @param str
	 * @param defaultValue
	 *            if failed,return defaultValue
	 * @return
	 */
	public static int strToInt(String str, int defaultValue) {
		try {
			defaultValue = Integer.parseInt(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static long strToLong(String str, long defaultValue) {
		try {
			defaultValue = Long.parseLong(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static float strToFloat(String str, float defaultValue) {
		try {
			defaultValue = Float.parseFloat(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static double strToDouble(String str, double defaultValue) {
		try {
			defaultValue = Double.parseDouble(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static BigDecimal strToBigDecimal(String str, BigDecimal defaultValue) {
		try {
			defaultValue = new BigDecimal(Double.parseDouble(str));
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static boolean strToBoolean(String str, boolean defaultValue) {
		try {
			defaultValue = Boolean.parseBoolean(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static java.util.Date strToDate(String str, java.util.Date defaultValue) {
		return strToDate(str, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	public static java.util.Date strToDate(String str, String format, java.util.Date defaultValue) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			defaultValue = dateFormat.parse(str);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static String dateToStr(java.util.Date date, String defaultValue) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss", defaultValue);
	}

	public static String dateToStr(java.util.Date date, String format, String defaultValue) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			defaultValue = dateFormat.format(date);
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * if the str is null or empty,return defaultValue
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String strToStr(String str, String defaultValue) {
		if (isNotBlank(str))
			defaultValue = str;
		return defaultValue;
	}

	public static Long objectToLong(Object obj, Long defaultValue) {
		try {
			defaultValue = Long.parseLong(String.valueOf(obj));
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static BigDecimal objectToBigDicimal(Object obj, BigDecimal defaultValue) {
		try {
			defaultValue = BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj)));
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	public static Double objectToDouble(Object obj, Double defaultValue) {
		try {
			defaultValue = Double.parseDouble(String.valueOf(obj));
		} catch (Exception localException) {
		}
		return defaultValue;
	}

	/**
	 * util.Date convert to sql.Date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date dateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * sql.Date convert to util.Date
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date sqlDateToDate(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}

	public static Timestamp dateToSqlTimestamp(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	public static java.util.Date sqlTimestampToDate(Timestamp date) {
		return new java.util.Date(date.getTime());
	}

	public static int strtoAsc(String str) {
		return str.getBytes()[0];
	}

	public static char intToChar(int backnum) {
		return (char) backnum;
	}

	/**
	 * 比较大小
	 * 
	 * @param v1
	 * @param v2
	 * @return -1:v1 小于 v2 0:v1 等于 v2 1: v1 大于 v2
	 */
	public static int comparison(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 把Url参数通过 < & = > 连接起来 sample: url:param=aa&address=bb
	 * 
	 * @param sList
	 * @return
	 */
	public static String joinUrlParameter(List<String> sList) {
		StringBuffer sBuf = new StringBuffer();
		for (Iterator<String> it = sList.iterator(); it.hasNext();) {
			sBuf.append("&").append(it.next()).append("=").append(it.next());
		}
		return sBuf.substring(1, sBuf.length());
	}

	/**
	 * 获得当前系统时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getSystemTime(String pattern) {
		if (isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		String str = "";
		java.util.Date date = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		str = dateFormat.format(date);
		return str;
	}

	/**
	 * @param src
	 *            源数组
	 * @param srcPos
	 *            源数组中的起始位置
	 * @param dest
	 *            目标数组
	 * @param destPos
	 *            目标数据中的起始位置
	 * @param length
	 *            要复制的数组元素的数量
	 */
	public static void copyArray(Object src, int srcPos, Object dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
	}

	/**
	 * 数组转List
	 * 
	 * @param arr
	 *            变长数组
	 * @return List
	 */
	public static <T> List<T> arrToList(T... arr) {
		return Arrays.asList(arr);
	}

	/**
	 * 获得对象的IdentityHashCode 在concurrent时 有嵌套锁时进行顺序的判断
	 * 
	 * @return
	 */
	public static int getObjectIdentityHashCode(Object object) {
		return System.identityHashCode(object);
	}

	/**
	 * 获得临时目录路径
	 * 
	 * @return
	 */
	public static String getTempDirectoryPath() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 获得路径分隔符
	 * 
	 * @return
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * 判断文件是否为图片
	 * 
	 * @param _tmp
	 */
	public static boolean isImage(File _tmp) {
		if (isEmpty(_tmp))
			return false;
		String regex = "(?i).+?\\.(jpg|gif|bmp)";
		return _tmp.getName().matches(regex);
	}

	/**
	 * 获得文件输入流
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * 供其他方法调用
	 * 
	 * @param input
	 * @param output
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
			throws IOException {
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {// EOF=-1
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * 从输入流到输出流
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output) throws IOException {
		return copyLarge(input, output, new byte[1024 * 4]);//
	}

	/**
	 * 读取指定输入流的数据到缓冲区
	 * 
	 * @param input
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static int read(InputStream input, byte[] buffer) throws IOException {
		return read(input, buffer, 0, buffer.length);
	}

	/**
	 * 供其他方法调用
	 * 
	 * @param input
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static int read(InputStream input, byte[] buffer, int offset, int length)
			throws IOException {
		if (length < 0) {
			throw new IllegalArgumentException("Length must not be negative: " + length);
		}
		int remaining = length;
		while (remaining > 0) {
			int location = length - remaining;
			int count = input.read(buffer, offset + location, remaining);
			if (-1 == count) { // EOF
				break;
			}
			remaining -= count;
		}
		return length - remaining;
	}

	/**
	 * 从指定文件读取数据到输出流
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static long copyFile(File input, OutputStream output) throws IOException {
		final FileInputStream fis = new FileInputStream(input);
		try {
			return copyLarge(fis, output);
		} finally {
			fis.close();
		}
	}

	/**
	 * 从指定文件复制到目的文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		copyFile(srcFile, destFile, true);
	}

	/**
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param preserveFileDate
	 *            true和copyFile(srcFile,destFile)相同
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile, boolean preserveFileDate)
			throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destFile == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (srcFile.exists() == false) {
			throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' exists but is a directory");
		}
		if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
			throw new IOException("Source '" + srcFile + "' and destination '" + destFile
					+ "' are the same");
		}
		File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
				throw new IOException("Destination '" + parentFile
						+ "' directory cannot be created");
			}
		}
		if (destFile.exists() && destFile.canWrite() == false) {
			throw new IOException("Destination '" + destFile + "' exists but is read-only");
		}
		doCopyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * 供方法调用
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param preserveFileDate
	 * @throws IOException
	 */
	public static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate)
			throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' exists but is a directory");
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel input = null;
		FileChannel output = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			input = fis.getChannel();
			output = fos.getChannel();
			long size = input.size();
			long pos = 0;
			long count = 0;
			while (pos < size) {
				count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
				pos += output.transferFrom(input, pos, count);
			}
		} finally {
			output.close();
			fos.close();
			input.close();
			fis.close();
		}

		if (srcFile.length() != destFile.length()) {
			throw new IOException("Failed to copy full contents from '" + srcFile + "' to '"
					+ destFile + "'");
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 *            开始文件
	 * @param destFile
	 *            目的文件
	 * @throws IOException
	 */
	public static void moveFile(File srcFile, File destFile) throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destFile == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' is a directory");
		}
		if (destFile.exists()) {
			throw new IOException("Destination '" + destFile + "' already exists");
		}
		if (destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' is a directory");
		}
		boolean rename = srcFile.renameTo(destFile);// 文件移动的主要方法
		if (!rename) {// 如果没有renameTo成功,则复制源文件到目的文件
			copyFile(srcFile, destFile);
			if (!srcFile.delete()) {
				destFile.delete();
				throw new IOException("Failed to delete original file '" + srcFile
						+ "' after copy to '" + destFile + "'");
			}
		}
	}

	/**
	 * 产生指定长度的随机字符串
	 * 
	 * @param length
	 *            任意长度 但是必须在0-36之间
	 * @return
	 */
	public static String generateSpecifyLengthUUIDStr(int length) {
		if (length <= 0 || length > 36)
			return "Undefined";
		return UUID.randomUUID().toString().replace("-", "").substring(0, length);
	}

	/**
	 * 产生指定长度的随机数字符串
	 * 
	 * @param lenth
	 * @return
	 */
	public static String generateSpecifyLengthRandomNumber(int length) {
		if (length <= 0)
			return String.valueOf((int) (Math.random() * 10));
		if (length > Integer.MAX_VALUE)
			length = Integer.MAX_VALUE;
		StringBuffer sb = new StringBuffer();
		int num = 0;
		while (length-- > 0) {
			num = (int) (Math.random() * 10);
			sb.append(num);
		}
		return sb.toString();
	}

	/**
	 * MD5加密算法 不可逆
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密算法 用generateRSAKeyMap生成的私鈅进行解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 用RSA加密 用generateRSAKeyMap生成的公鈅进行加密
	 * 
	 * @param publicKey
	 * @param srcBytes
	 * @return
	 */
	public static byte[] encryptRSA(Object publicKey, byte[] srcBytes) {
		byte[] resultBytes = null;
		if (isEmpty(srcBytes) || isEmpty(publicKey)) {
			return null;
		}
		try {
			// Cipher负责完成加密或解密操作，基于RSA
			Cipher cipher = Cipher.getInstance(KEY_RSA);
			// 根据公钥，对Cipher对象进行初始化
			cipher.init(Cipher.ENCRYPT_MODE, (RSAPublicKey) publicKey);
			// 加密，结果保存进resultBytes
			resultBytes = cipher.doFinal(srcBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultBytes;
	}

	/**
	 * 对RSA解密
	 * 
	 * @param privateKey
	 * @param encBytes
	 * @return
	 */
	public static byte[] decryptRSA(Object privateKey, byte[] encBytes) {
		if (isEmpty(privateKey) || isEmpty(encBytes)) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(KEY_RSA);
			// 根据秘钥，对Cipher对象进行初始化
			cipher.init(Cipher.DECRYPT_MODE, (RSAPrivateKey) privateKey);
			// 解密，结果保存进resultBytes
			byte[] decBytes = cipher.doFinal(encBytes);
			return decBytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param mobileNo
	 * @return true or false
	 */
	public static boolean isMobilePhone(String mobileNo) {
		Pattern pattern = Pattern
				.compile("^0?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher matcher = pattern.matcher(mobileNo);
		return matcher.matches();
	}

	/**
	 * 是否为IP
	 * 
	 * @param ip
	 * @return true or false
	 */
	public static boolean isIP(String ip) {
		if ("localhost".equals(ip)) {
			return true;
		}
		Pattern pattern = Pattern.compile("" + "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"
				+ "\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\."
				+ "(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\."
				+ "(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * 邮箱是否合法
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern pattern = Pattern
				.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * 生成指定长度的公鈅和私钥 可通过Map对应指定的Key(PRIVATE_KEY、PUBLIC_KEY)获得
	 * 
	 * @param initialSize
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String, Object> generateRSAKeyMap(int initialSize)
			throws NoSuchAlgorithmException {
		if (initialSize < 0 || initialSize > Integer.MAX_VALUE) {
			initialSize = 1024;
		}
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥,密钥大小为
		keyPairGenerator.initialize(initialSize);
		// 生􁟤一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PRIVATE_KEY, privateKey);
		map.put(PUBLIC_KEY, publicKey);
		return map;
	}

	/* 默认除法运算精度 */
	private static final int DEF_DIV_SCALE = 10;
	/* md5加密对象 */
	@SuppressWarnings("unused")
	private static MessageDigest messageDigest;
	/* 给每个线程创建一个容器 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private static ThreadLocal threadLocal = new ThreadLocal();
	/* SHA加密算法 */
	private static final String KEY_SHA = "SHA";
	/* RSA对称加密算法 */
	@SuppressWarnings("unused")
	private static final String KEY_RSA = "RSA";
	/* MD5加密算法 */
	private static final String KEY_MD5 = "MD5";
	/* RSA私钥 */
	private static final String PRIVATE_KEY = "privateKey";
	/* RSA公钥 */
	private static final String PUBLIC_KEY = "publicKey";
	/* 文件缓冲区 */
	private static final Long FILE_COPY_BUFFER_SIZE = 1024 * 1024 * 30L;

}
