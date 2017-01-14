package com.thanple.thinking.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Process为处理命令行的进程
 * javap：jdk自带反编译器
 * */
public class OSExecute {

	public static void command(String command) throws Exception{
		boolean err = false;
		try {
			Process process =  new ProcessBuilder(command.split(" ")).start();
			
			//get commands inputstream
			BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
			for(String temp = null; (temp=results.readLine()) != null ; ){
				System.out.println(temp);
			}
			
			//get error inputstream
			BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			for(String temp = null; (temp=errors.readLine()) != null ; ){
				System.out.println(temp);
				err = true;
			}	
			
		} catch (IOException e) {
			if(!command.startsWith("CMD /C")){
				command("COMD /C "+command);
			}else{
				throw new RuntimeException(e);
			}
		}
		
		if(err){
			throw new Exception("Error executing "+command);
		}

	}

	public static void main(String[] args) throws Exception {
		OSExecute.command("javap OSExecute");

	}

}
