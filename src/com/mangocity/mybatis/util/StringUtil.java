package com.mangocity.mybatis.util;

import java.util.regex.Pattern;
/**
 * 
* @ClassName: StringUtil 
* @Description: (字符串工具类) 
* @author 刘春元
* @date 2015年6月16日 下午1:48:40 
*
 */
public class StringUtil {
	/**
	 * 字符串正则匹配方法
	 * @param string 要匹配的字符串
	 * @param regex 正则表达式
	 * @return
	 */
	public static boolean stringFilter(String string, String regex) {
		Pattern p = Pattern.compile(regex);
		return p.matcher(string).matches();
	}
}
