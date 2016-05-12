package com.mangocity.mybatis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class New {
	public static <K, V> Map<K, V> map() {
		return new HashMap<K, V>();
	}

	public static <K, V> Map<K, V> lMap() {
		return new LinkedHashMap<K, V>();
	}

	public static <K, V> Map<K, V> cMap() {
		return new ConcurrentHashMap<K, V>();
	}

	public static <K> Set<K> set() {
		return new HashSet<K>();
	}

	public static <K> List<K> list() {
		return new ArrayList<K>();
	}

	public static <K> LinkedList<K> lList() {
		return new LinkedList<K>();
	}

	public static <K> Queue<K> queue() {
		return new LinkedList<K>();
	}
}
