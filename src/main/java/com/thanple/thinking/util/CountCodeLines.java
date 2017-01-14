package com.thanple.thinking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 计算多少行代码量
 * fileType文件后缀名,不用该参数则表示所有文件类型
 * */
public class CountCodeLines {
	
	private File file;
	private String fileType;
	private long lines = 0;
	
	public CountCodeLines(String file,String fileType) throws FileNotFoundException {
		this.file = new File(file);
		this.fileType = fileType;
	}
	public CountCodeLines(String file) {
		this.file = new File(file);
	}
	
	public long startCount() throws FileNotFoundException {	
		if(file==null) {
			System.out.println("文件/目录不存在");
			return 0;
		}
		return this.count(file);
	}
	@SuppressWarnings("resource")
	public long count(File fileParam) throws FileNotFoundException {
		if(fileParam.isDirectory()) {
			for(File eachFile : fileParam.listFiles()) {
				this.count(eachFile);
			}
		}else {
			if(fileType!=null) {
				String fileName = fileParam.getName();
				String tempFileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				
				if(!fileType.equals(tempFileType)){
					return 0;
				}
			}
			
			Scanner readScanner = new Scanner(new FileInputStream(fileParam));
			while(readScanner.hasNext()) {
				readScanner.nextLine();
				lines++;
			}
		}
		
		return lines;
		
	}
	
	public void printCount() {
		System.out.println("总共"+lines+"行代码");
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//CountCodeLines c = new CountCodeLines("F:\\J2EE Project\\JeeCMS\\src", "java");
		CountCodeLines c1 = new CountCodeLines("D:\\wamp\\www\\NewMarketing\\App\\Home","php");
		
		CountCodeLines c2 = new CountCodeLines("D:\\wamp\\www\\NewMarketing\\App\\Admin","php");
		
		CountCodeLines c3 = new CountCodeLines("D:\\wamp\\www\\NewMarketing\\App\\Common","php");
		
		CountCodeLines c4 = new CountCodeLines("D:\\wamp\\www\\NewMarketing\\App","html");
		
		CountCodeLines c5 = new CountCodeLines("D:\\wamp\\www\\schoolactivity\\App","html");
		
		System.out.println("总共"+(c1.startCount()+c2.startCount()+c3.startCount()+c4.startCount())+"行代码");
		c1.printCount();
		c2.printCount();
		c3.printCount();
		c4.printCount();
		c5.startCount();c5.printCount();
		
		
		
		/*
		CountCodeLines c4 = new CountCodeLines("F:\\J2EE Project\\ShopsshEx\\src\\com");
		c4.startCount();
		c4.endCount();*/
	}

}
