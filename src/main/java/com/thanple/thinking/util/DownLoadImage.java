package com.thanple.thinking.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class DownLoadImage {
	
	//从特定地址输出图片
	public static void create(URL url,String path,String name){
		
		File dictionary = new File(path);
		if(!dictionary.exists()){
			dictionary.mkdirs();
		}
		File file = new File(path+"\\"+name);
		
		InputStream input = null;
		OutputStream output = null;
		try {
			//输入流
			input = url.openStream();
			
			//输出流
			output = new FileOutputStream(file);

			//一个字节一个字节读
			int readByte;
			while((readByte = input.read())!=-1){
				output.write(readByte);	
			}		
			
		} catch (IOException e) {
			file.delete();	//失败了就删除文件
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public static void create(String url,String dictionary,String name){
		try {
			create(new URL(url) , dictionary,name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	/*
	 * 从特定html中取出字符串
	 * 
	 * */
	//从特定html中取出字符串(只提取一个);
	public static String fetchString(String source,String start,String end){
		
		int first  = source.indexOf(start);
		int last = source.indexOf(end,first+1);
		
		if(first==-1 || last==-1)
			return "";
		return (source.substring(first+1, last));	
	}
	//从第几个标志位开始取字符串 如：下面从第三个引号开始
	//<div class="picContent"><img src="http://img.tp123456.com/tubaobao/蕾丝兔宝宝合集2011/紫色红色纱裙/1.jpg"><br>
	public static String fetchString(String source,String start,
			String end,int startSign){
		
		int first=-1;
		for(int i=0;i<startSign;i++){
			first = source.indexOf(start,first+1);
		}
		int last = source.indexOf(end, first+1);
		
		return source.substring(first+1, last);
	}
	
	//从特定html中取出字符串(提取多个)
	public static ArrayList<String> fetchString(String source,String start,
			String end,String modifySign){
		int first=-1,last=-1;
		ArrayList<String> target = new ArrayList<String>();
		
		do{
			first = source.indexOf(start,last+1);
			last = source.indexOf(end, first+1);	
		}while(first!=-1 && last!=-1 && target.add(source.substring(first+1, last)));

		return target;
	}
	//从多行html中提取出多个
	public static ArrayList<String> fetchString(Scanner input,String start,String end,
			int times){
		ArrayList<String> target = new ArrayList<String>();
		for(int i=0;i<times;i++){	
			String add = fetchString(input.nextLine(),start,end);
			if(!add.equals(""))
				target.add(add);
		}
		
		return target;
	}
	//从html未知行数内提取出多个(以endWith为结束标志)
	public static ArrayList<String> fetchString(Scanner input,String start,String end,
			String endWith,int startSign){
		
		ArrayList<String> target = new ArrayList<String>();
		
		String read=null; 
		while((read=input.nextLine()).indexOf(endWith)==-1){
			String add = fetchString(read,start,end);
			if(!add.equals(""))
				target.add(add);
			startSign = 1;
		}
		
		return target;	
	}
	
	//获取标题(从第几行字符串中获取)
	public static String getTitle(Scanner input,String start,String end,int line){
		for(int i=0;i<line-1;i++){
			input.nextLine();
		}
		return fetchString(input.nextLine(),start,end);
	}
	
	//放过几行的输入
	public static void wasteScanner(Scanner input,int fromLine,int toLine){
		for(int i=fromLine;i<=toLine;i++){
			input.nextLine();
		}
	}
	
	//获取一个url输入流
	public static Scanner getScanner(String urlString,String encoding) 
			throws MalformedURLException, IOException{
		
		return new Scanner(new URL(urlString).openStream(),encoding);
	}
	
}
