package com.thanple.thinking.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {
	
	private static final int BSIZE = 1024;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		//FileOutputStream
		FileChannel fc = new FileOutputStream("src/com/thanple/resource/test.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		fc.close();
		
		//RandomAccessFile
		fc = new RandomAccessFile("test.txt", "rw").getChannel();
		fc.position(fc.size());		//Move to the end
		fc.write(ByteBuffer.wrap("Some more".getBytes()));
		fc.close();
		
		//FileInputStream
		fc = new FileInputStream("test.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();
		while(buff.hasRemaining()){
			System.out.println((char)buff.get());
		}
		fc.close();
	}

}
