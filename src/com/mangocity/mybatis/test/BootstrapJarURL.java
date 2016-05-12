package com.mangocity.mybatis.test;

import java.net.URL;

import sun.misc.Launcher;

public class BootstrapJarURL {

	public static void main(String[] args) {
		URL[] urls = Launcher.getBootstrapClassPath().getURLs();  
		for (int i = 0; i < urls.length; i++) {  
		    System.out.println(urls[i].toExternalForm());  
		}
		
		System.setProperty("sun.boot.class.path", "rt.jar");
		System.out.println(System.getProperty("sun.boot.class.path"));
	}

}
