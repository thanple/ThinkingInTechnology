package com.thanple.thinking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchField {

	public static void main(String[] args) throws FileNotFoundException {
		findField(new File("F:\\AndroidStudioProjects\\MicroBlog\\app\\src"),
				"ookie");
		
		//findField(new File("F:\\Workspaces\\Workspace2014\\dcp_sis"),"thanpleModule");
		System.out.println("结束搜索");
	}
	
	public static void findField(File file,String field) throws FileNotFoundException{
		if(file.isDirectory()){
			for(File eachFile:file.listFiles())		
				findField(eachFile,field);
		}else{
			if(isFinded(file, field)){
				System.out.println(file);
			}
		}
	}
	
	public static boolean isFinded(File file,String field) {
		Scanner input = null;
		try {
			input = new Scanner(new FileInputStream(file));
			while(input.hasNext()){
				String temp = input.nextLine();
				if(temp.contains(field)){
					return true;
				}					
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			input.close();
		}
		return false;	
	}

}
