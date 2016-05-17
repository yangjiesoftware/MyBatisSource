package com.mangocity.mybatis.test.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UserInfoClient {

	public static void main(String[] args) throws Exception {
		/*
		 * Constructor constructor =
		 * UserInfo.class.getConstructor(String.class); UserInfo userInfo =
		 * (UserInfo)constructor.newInstance("hello"); userInfo.getUserInfo();
		 * 
		 * Constructor constructors = UserInfo.class.getConstructor(); UserInfo
		 * userInfos = (UserInfo)constructors.newInstance();
		 * userInfos.getUserInfo();
		 */
		Object obj= Class.forName(UserInfo.class.getName()).newInstance();
		
		/*Constructor constructors = UserInfo.class.getConstructor(); UserInfo
		userInfos = (UserInfo)constructors.newInstance();
		userInfos.getUserInfo();*/

		Class<?> clazz = Class.forName(UserInfo.class.getName());
		// printClass(clazz);

		/*Constructor constructors = clazz
				.getConstructor(String.class, int.class);
		UserInfo userInfo = (UserInfo) constructors.newInstance("hello", 4);
		userInfo.getUserInfo();*/
	}

	private static void printClass(Class<?> clazz) {
		for (Method tMethod : clazz.getDeclaredMethods()) {
			System.out.println(tMethod.getName() + "-" + tMethod.getModifiers()
					+ "-" + tMethod.getParameterTypes());
		}
		System.out.println("=====================================");
		for (Field tField : clazz.getDeclaredFields()) {
			System.out.println(tField.getName() + "-" + tField.getModifiers());
		}
		System.out.println("=====================================");
		for (Constructor<?> tConstructors : clazz.getDeclaredConstructors()) {
			System.out.print(tConstructors.getName() + "-"
					+ tConstructors.getModifiers());
			System.out.println("-");
			printMultiClass(tConstructors.getParameterTypes());
		}
	}

	private static void printMultiClass(Class<?>[] parameterTypes) {
		for (Class<?> tClazz : parameterTypes) {
			System.out.println(tClazz.getSimpleName());
		}
	}

}
