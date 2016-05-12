package com.mangocity.mybatis.util;

import java.util.HashSet;
import java.util.Set;

public class Sets {
	public static <T> Set<T> union(Set<T> a,Set<T> b){
		Set<T> result = new HashSet<T>(a);
		result.addAll(b);
		return result;
	}
	
	public static <T> Set<T> intersection(Set<T> a,Set<T> b){
		Set<T> result = new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}
	
	public static <T> Set<T> diff(Set<T> a,Set<T> b){
		Set<T> result = new HashSet<T>(a);
		result.removeAll(b);
		return result;
	}
	
	/**
	 * 取交集之外的其他元素
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> complement(Set<T> a,Set<T> b){
		return diff(union(a,b),intersection(a,b));
	}
}
