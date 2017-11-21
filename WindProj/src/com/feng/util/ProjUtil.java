package com.feng.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 * 判断是否是数字
 * 替换掉字符串中的空格、回车、换行和制表符
 * 检查字符串是否为空.值为null或长度为0都为空
 * 检查字符串是否不为空.要求val不为空并且val的长度大于0
 * int值转换成String
 * float值转换成String
 * double值转换成String 
 * double值转换成String,兼容了1111111111E10这种类型
 * long值转换成String
 * short值转换成String
 * boolean值转换成String
 * 将Long数组转换成逗号分隔的字串
 * 将List列表转换成字符串,取得列表里的每个对象调用其toString()方法
 * 将对象数组转换成String
 * 将重复的数据从ArrayList去除
 * 将重复的数据从String去除
 * 将重复的数据从String[]去除
 * 将重复的数据从Integer[]去除
 * 半角字符转全角字符
 * 全角字符转半角字符
 */
public class ProjUtil {
	//判断是否是数字
	public static boolean isNum(String str){
	        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	//替换掉字符串中的空格、回车、换行和制表符
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\t|\\r|\\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
			//dest=dest.replaceFirst("\"", "\\\"");
		}
		return dest;
	}
	//检查字符串是否为空.值为null或长度为0都为空
	public static boolean isEmpty(String val) {
		if (val == null || val.length() == 0) {
			return true;
		}
		return false;
	}
	//检查字符串是否不为空.要求val不为空并且val的长度大于0
	public static boolean isNotEmpty(String val) {
		if (val == null || val.length() == 0) {
			return false;
		}
		return true;
	}
	//int值转换成String
	public static String toString(int val) {
		return Integer.toString(val);
	}
	//float值转换成String
	public static String toString(float val) {
		return Float.toString(val);
	}
	//double值转换成String
	public static String toString(double val) {
		return Double.toString(val);
	}
	//double值转换成String,兼容了1111111111E10这种类型
	public static String fromDouble(double val) {
		String str = Double.toString(val);
		if (str.indexOf('E') >= 0) {
			String tail = str.substring(str.indexOf('E') + 1);
			String killtail = str.substring(0, str.indexOf('E'));
			int point = str.indexOf('.');
			int itail = Integer.valueOf(tail).intValue();
			int zreonum = itail;
			if (itail > 0) {
				if (point >= 0) {
					int behindpoint = killtail.length() - (point + 1);
					killtail = killtail.replace(".", "");
					zreonum = itail - behindpoint;
				}
				for (int i = 0; i < zreonum; i++) {
					killtail = killtail + "0";
				}
				if (zreonum < 0) {
					killtail = killtail.substring(0, killtail.length()
							+ zreonum)
							+ "."
							+ killtail.substring(killtail.length() + zreonum);
				}
				str = killtail;
			} else {
				str = "0";
			}
		}
		return str;
	}
	//long值转换成String
	public static String toString(long val) {
		return Long.toString(val);
	}
	//short值转换成String
	public static String toString(short val) {
		return Short.toString(val);
	}
	//boolean值转换成String
	public static String toString(boolean val) {
		return Boolean.toString(val);
	}
	//将Long数组转换成逗号分隔的字串
	public static String toCommaString(Long[] longArray) {
		if (longArray == null || longArray.length <= 0) {
			return "";
		}
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < longArray.length; i++) {
			buff.append(longArray[i]);
			if (i != longArray.length - 1) {
				buff.append(",");
			}
		}
		return buff.toString();
	}
	//将List列表转换成字符串,取得列表里的每个对象调用其toString()方法
	public static String listToString(List list, String itemName) {
		if (null == list || list.isEmpty()) {
			return "";
		} else {
			int size = list.size();
			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < size; i++) {
				buff.append(list.get(i).toString()).append("\n");
			}
			return buff.toString();
		}
	}
	//将对象数组转换成String
	public static String arrayToString(Object[] objs) {
		if (null == objs || objs.length == 0) {
			return "";
		} else {
			int size = objs.length;
			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < size; i++) {
				buff.append(objs[i].toString()).append("\n");
			}
			return buff.toString();
		}
	}
	//将重复的数据从ArrayList去除
	public static String[] delRepeatData(ArrayList al) {
		Set set = new HashSet(al);
		Object objs[] = set.toArray(new String[0]);
		return (String[]) objs;
	}
	//将重复的数据从String去除
	public static String delRepeatData(String str) {
		ArrayList al = new ArrayList();
		StringBuffer dataBuf = new StringBuffer();
		if (str != null && str.length() >= 1) {
			StringTokenizer st = new StringTokenizer(str, ",");
			while (st.hasMoreTokens()) {
				al.add(st.nextToken());
			}
			String singleDateArr[] = delRepeatData(al);
			if (singleDateArr != null) {
				for (int i = 0; i < singleDateArr.length; i++) {
					dataBuf.append(singleDateArr[i]);
					if (i != singleDateArr.length - 1) {
						dataBuf.append(",");
					}
				}
			}

		}
		return dataBuf.toString();
	}
	//将重复的数据从String[]去除
	public static String[] delRepeatData(String[] strArr) {
		ArrayList al = new ArrayList();
		if (strArr != null) {
			for (int i = 0; i < strArr.length; i++) {
				al.add(strArr[i]);

			}
		}
		String singleDateArr[] = delRepeatData(al);
		return singleDateArr;
	}
	//将重复的数据从Integer[]去除
	public static Integer[] delRepeatData(Integer[] intArr) {
		ArrayList al = new ArrayList();

		if (intArr != null) {
			for (int i = 0; i < intArr.length; i++) {
				al.add(intArr[i]);
			}
		}
		Set set = new HashSet(al);
		Object objs[] = set.toArray(new Integer[0]);
		return (Integer[]) objs;
	}
	//将重复的数据从Long[]去除
	public static Long[] delRepeatData(Long[] intArr) {
		ArrayList al = new ArrayList();

		if (intArr != null) {
			for (int i = 0; i < intArr.length; i++) {
				al.add(intArr[i]);
			}
		}
		Set set = new HashSet(al);
		Object objs[] = set.toArray(new Long[0]);
		return (Long[]) objs;
	}

	/**
	 * 上面的方法不好用，必须先构造数组，改为下面的一系列方法，支持到15个参数
	 * 
	 * @param pattern
	 *            message format pattern
	 * @param arg1
	 *            arguments
	 * @return the formatted string
	 */
	public static String stringFormat(String pattern) {
		return pattern;
	}

	public static String stringFormat(String pattern, Object arg1) {
		Object[] argList = new Object[] { arg1 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2) {
		Object[] argList = new Object[] { arg1, arg2 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3) {
		Object[] argList = new Object[] { arg1, arg2, arg3 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10, arg11 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10, arg11, arg12 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12,
			Object arg13) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10, arg11, arg12, arg13 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12,
			Object arg13, Object arg14) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14 };
		return MessageFormat.format(pattern, argList);
	}

	public static String stringFormat(String pattern, Object arg1, Object arg2,
			Object arg3, Object arg4, Object arg5, Object arg6, Object arg7,
			Object arg8, Object arg9, Object arg10, Object arg11, Object arg12,
			Object arg13, Object arg14, Object arg15) {
		Object[] argList = new Object[] { arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15 };
		return MessageFormat.format(pattern, argList);
	}
	//半角字符转全角字符
	public static String toDBCS(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = str.length(); i < n; i++) {
			int c = str.charAt(i);
			if ((c >= 'a') && (c <= 'z')) {
				c = (c + 'ａ') - 'a';
			} else if ((c >= 'A') && (c <= 'Z')) {
				c = (c + 'Ａ') - 'A';
			} else if ((c >= '0') && (c <= '9')) {
				c = (c + '０') - '0';
			}
			sb.append((char) c);
		}
		return sb.toString();
	}
	//全角字符转半角字符
	public static String toSBCS(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = str.length(); i < n; i++) {
			int c = str.charAt(i);
			if ((c >= 'Ａ') && (c <= 'Ｚ')) { // A ..
				c = (c + 'A') - 'Ａ';
			} else if ((c >= 'ａ') && (c <= 'ｚ')) { // a ..
				c = (c + 'a') - 'ａ';
			} else if ((c >= '０') && (c <= '９')) { // 0 ..
				c = (c + '0') - '０';
			} else if (c == '”') { // 双引号
				c = '"';
			} else if (c == '“') {
				c = '"';
			} else if (c == '＜') { // 小于号
				c = '<';
			} else if (c == '＞') {
				c = '>';
			} else if (c == '’') { // 单引号
				c = '\'';
			} else if (c == '‘') {
				c = '\'';
			} else if (c == '，') { // 逗号
				c = ',';
			} else if (c == '；') { // 分号
				c = ';';
			} else if (c == '。') {
				c = '.';
			} else if (c == '＆') {
				c = '&';
			}
			sb.append((char) c);
		}
		return sb.toString();
	}
}
