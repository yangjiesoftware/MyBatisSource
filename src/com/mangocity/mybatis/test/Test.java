package com.mangocity.mybatis.test;

import java.io.File;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		 String userDir = System.getProperty("user.dir");
		 System.out.println(userDir);//工作区目录
		 
		 File file = new File("src","sqlconfig.xml");
		 System.out.println(file.getAbsolutePath());
		 System.out.println(file.getCanonicalFile());
		 
		 System.out.println("==========Class.forName===========begin");
		 Class clazz = Class.forName(Test.Person.class.getName());
		 System.out.println(clazz);
	}
	
	public static class Person{
		public Person(){
			System.out.println("person 构造...");
		}
		static{
			System.out.println("person static");
		}
	}

}
