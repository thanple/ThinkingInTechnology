package com.thanple.thinking.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel : NIO
 * ByteBuffer: NIO
 * */
public class ChannelCopy {

	private static final int BSIZE = 1024;
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		FileChannel 
			in = new FileInputStream("src/com/thanple/resource/test.txt").getChannel(),
			out = new FileOutputStream("src/com/thanple/resource/test2.txt").getChannel();
		
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		while(in.read(buffer) != -1){
			buffer.flip();		//Prepare for writing
			out.write(buffer);
			buffer.clear();		//Prepare for reading
		}
		
		System.out.println("finish");
	}

}
