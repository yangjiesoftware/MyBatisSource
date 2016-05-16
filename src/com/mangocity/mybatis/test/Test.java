package com.mangocity.mybatis.test;

import java.io.File;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		 String userDir = System.getProperty("user.dir");
		 System.out.println(userDir);//工作区目录
		 
		 File file = new File("src","sqlconfig.xml");
		 System.out.println(file.getAbsolutePath());
		 System.out.println(file.getCanonicalFile());
	}

}
