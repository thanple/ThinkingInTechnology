package com.thanple.thinking.jvm;

import java.net.URL;

/**
 * ClassLoader 分为：
 * 1.Bootstrap ClassLoader:称为启动类加载器，是Java类加载层次中最顶层的类加载器，
 * 				负责加载JDK中的核心类库，如：rt.jar、resources.jar、charsets.jar。
 * 2.Extension ClassLoader：称为扩展类加载器，负责加载Java的扩展类库，默认加载JAVA_HOME/jre/lib/ext/目下的所有jar。
 * 3.App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件。
 * 
 * */
public class ClassLoaderTest {

	public static void main(String[] args) throws ClassNotFoundException {

		//Bootstrap ClassLoader
		System.out.println("Bootstrap ClassLoader:");
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();  
		for (int i = 0; i < urls.length; i++) {  
		    System.out.println(urls[i].toExternalForm());  
		}
		
		System.out.println(System.getProperty("sun.boot.class.path"));
		
		System.out.println(ClassLoader.getSystemClassLoader().loadClass(ClassLoaderTest.class.getName()));
			
	}

}
