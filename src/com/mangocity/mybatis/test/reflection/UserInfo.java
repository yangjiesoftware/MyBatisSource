package com.mangocity.mybatis.test.reflection;

public class UserInfo {
	private String userName;

	private int age;

	public UserInfo(String userName) {
		super();
		System.out.println("UserInfo 有参构造函数");
		this.userName = userName;
	}

	public UserInfo(String userName, int age) {
		super();
		System.out.println("UserInfo(String userName, int age)..." + userName + "--" + age);
		this.userName = userName;
		this.age = age;
	}

	public UserInfo() {
		System.out.println("UserInfo 无参构造函数");
	}

	private void showPrivate() {
		System.out.println("showPrivate");
	}

	public void getUserInfo() {
		System.out.println("getUserInfo...");
	}
}
