package com.thanple.thinking.jvm;

import java.io.PrintStream;

public class System_summary {

	public static void main(String[] args) {

		PrintStream out = System.out;
		out.append("a\n");
		
		
		System.out.println(System.getenv("home"));
	}

}
