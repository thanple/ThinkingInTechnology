package com.thanple.thinking.util;

import java.io.File;
import java.io.FileNotFoundException;

/*
 * 计算一个文件夹内有多少文件
 * */
public class CountFiles {
	private File file;			//文件目录或文件
	private String fileType;	//文件类型
	private long count = 0;		//文件量
	
	public CountFiles(String file,String fileType) throws FileNotFoundException {
		this.file = new File(file);
		this.fileType = fileType;
	}
	public CountFiles(String file) {
		this.file = new File(file);
	}
	
	public void startCount() throws FileNotFoundException {	
		if(file==null) {
			System.out.println("文件/目录不存在");
			return;
		}
		this.count(file);
	}
	
	public void count(File fileParam) throws FileNotFoundException {
		if(fileParam.isDirectory()) {
			for(File eachFile : fileParam.listFiles()) {
				this.count(eachFile);
			}
		}else {
			if(fileType!=null) {
				String fileName = fileParam.getName();
				String tempFileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				
				if(!fileType.equals(tempFileType)){
					return;
				}
			}	
			count++;
		}
	}
	public void endCount() {
		System.out.println("总共"+count+"个文件");
		file = null;
		count = 0;
	}

	public static void main(String[] args) throws FileNotFoundException {
		//CountFiles c = new CountFiles("F:\\J2EE Project\\JeeCMS\\src","java");
		CountFiles c = new CountFiles("F:\\J2EE Project\\JeeCMS\\src");
		c.startCount();
		c.endCount();
		
	}

}
