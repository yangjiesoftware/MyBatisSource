package com.mangocity.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceClassLoader {

	public static void main(String[] args) throws IOException {
		InputStream in = ResourceClassLoader.class.getResourceAsStream("/config/db.properties");
		printInputStreamProps(in);
		
		InputStream in2 = ResourceClassLoader.class.getResourceAsStream("config/db.properties");
		printInputStreamProps(in2);
	}

	private static void printInputStreamProps(InputStream in)
			throws IOException {
		Properties props = new Properties();
		props.load(in);
		System.out.println(props.get("driver.sqllite"));
	}
	
	public static String getUserName(){
		return "say Hello, " + "123";
	}
	
	public static int sayHello(){
		System.out.println("what can I do for you.");
		return 1;
	}
}
