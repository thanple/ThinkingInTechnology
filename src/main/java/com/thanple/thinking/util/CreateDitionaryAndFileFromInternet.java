package com.thanple.thinking.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/*
 * 将网站上的资源拷贝到本地，在本地创建文件夹和文件
 * */
public class CreateDitionaryAndFileFromInternet {
	
	/*
	 * 将网上的文件下载到本地(如js.css等)
	 * */
	public static boolean downLoadText(URL url,String dictionary,String fileName) throws IOException{
		
		//创建文件夹
		File dic = new File(dictionary);
		if(dic.exists()){
			return false;
		}
		dic.mkdirs();
		
		//创建文件输出流
		File file = new File(dictionary+"\\"+fileName);
		PrintWriter output = new PrintWriter(file);
		
		//将网络文件输入流写入文件输出流
		InputStream input = url.openStream();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(input);
		while(reader.hasNextLine()){
			output.println(reader.nextLine());
		}
		
		input.close();
		output.close();
		
		return true;
	}
	
	public static void execute(String url,String dic) throws MalformedURLException, IOException{
		String [] arr = url.split("/");
		
		String dictionary = dic;
		for(int i=2;i<arr.length-1;i++){
			dictionary += "\\"+arr[i];
		}
		String fileName = arr[arr.length-1];
		
		System.out.println("目录:"+dictionary);
		System.out.println("创建文件"+fileName+"中");
		
		if(downLoadText(new URL(url),dictionary,fileName)){
			System.out.println("下载成功");
		}else{
			System.out.println("失败");
		}
	}
//http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/fonts/fontawesome-webfont.woff2?v=4.4.0
	public static void main(String[] args) throws MalformedURLException, IOException {
		execute("http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js",
				"F:\\IdeaProjects\\IOVServer\\web\\front");
	}
	
	
}
