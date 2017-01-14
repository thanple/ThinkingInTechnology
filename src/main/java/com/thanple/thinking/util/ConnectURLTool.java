package com.thanple.thinking.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/*
 * 连接网络URL工具
 * 
 * 1.格式:构造函数->writeString()->getScannerString();
 * 2.中间可以修改URLConnection
 * 3.默认网站编码"utf-8"，可以通过函数更改
 * 
 * */

public class ConnectURLTool {
	
	private HttpURLConnection connection;
	private String encoding="utf-8";
	private String stringScanner;
	
	
	public ConnectURLTool(URL url) throws IOException{
		connection = (HttpURLConnection)url.openConnection();
		this.setAllYouNeed();
	}
	public ConnectURLTool(String urlString) throws MalformedURLException, IOException{
		connection = (HttpURLConnection)new URL(urlString).openConnection();
		this.setAllYouNeed();
	}
	
	//允许想url输入输出
	private void setAllYouNeed() throws ProtocolException{
		stringScanner = null;	//内置读入字符串为空
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setConnectTimeout(5000);
		connection.setInstanceFollowRedirects(true);
		HttpURLConnection.setFollowRedirects(true);
		//connection.setRequestMethod("POST");
		//connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)"); 
	}
	
	//向服务器写入字符串
	public void writeString(String send) throws IOException{
		if(connection==null){
			throw new IOException("没有连接URL");
		}
		PrintWriter out = new PrintWriter(connection.getOutputStream());
		
		out.print(send);
		
		out.flush();
		out.close();
	}
	
	//获取服务器端返回的
	public String getScannerString() throws IOException{
		if(connection==null){
			throw new IOException("没有连接URL");
		}
		
		if(stringScanner==null){
			Scanner input = new Scanner(connection.getInputStream(),encoding);
			
			StringBuilder response = new StringBuilder();
			while(input.hasNext()){
				response.append(input.nextLine());
				response.append("\n");
			}
			input.close();
			stringScanner = new String(response);
		}
		
		return stringScanner;
	}
	
	public String getScannerString(int lineNumber) throws IOException {
		if(connection==null){
			throw new IOException("没有连接URL");
		}
	
		String returnLine=null;
		
		if(stringScanner==null){
			Scanner input = new Scanner(connection.getInputStream(),encoding);
			for(int i=0;i<lineNumber-1;i++){
				input.nextLine();
			}
			returnLine = input.nextLine();
			input.close();
		}else{
			returnLine = stringScanner.split("\n")[lineNumber-1];
		}

		return returnLine;
	}
	
	public void printString()throws IOException{
		int count=1;
		for(String eachLine:this.getScannerString().split("\n")){
			System.out.println(count+"."+eachLine);
			count++;
		}
	}

	
	/*
	 * getter & setter
	 * */


	public HttpURLConnection getConnection() {
		return connection;
	}
	public void setConnection(HttpURLConnection connection) throws ProtocolException {
		this.connection = connection;
		this.setAllYouNeed();
	}
	public void setConnection(String connectionString) throws MalformedURLException, IOException {
		this.connection = (HttpURLConnection)new URL(connectionString).openConnection();
		this.setAllYouNeed();
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public URL getURL(){
		return connection==null?null:connection.getURL();
	}

}
