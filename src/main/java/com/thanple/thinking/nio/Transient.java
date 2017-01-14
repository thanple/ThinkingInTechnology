package com.thanple.thinking.nio;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;





/**
 * 序列化：实现Serializeble，加transient可使对象不被序列化。transient:短暂的，路过的。
 * 实现Externalizable可自定义该序列化的，不该序列化的(该类需要有默认构造函数)。
 * 
 * 存储方式：
 * 	Serializable：对象完全以二进制存储为构造基础，不调用构造器
 *  Externalizable：先初始化对象，然后再调用readExternal()恢复对象。
 * */
class Model implements Serializable{
	private static final long serialVersionUID = 1884555571119999794L;
	
	private Date date = new Date();
	private String name;
	private transient String password;
	
	public Model(String name , String password) {
		this.name = name;
		this.password  = password;
	}
	
	@Override
	public String toString() {
		return "name="+name+",password="+password+",date="+date;
	}
}

class Model2 implements Externalizable{

	private Date date = new Date();
	private String name;
	private String password;
	
	public Model2() {}	//该构造函数不可缺省
	public Model2(String name , String password) {
		this.name = name;
		this.password  = password;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(name);
		out.writeObject(date);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		this.name = (String)in.readObject();
		this.date = (Date)in.readObject();
	}
	
	
	@Override
	public String toString() {
		return "name="+name+",password="+password+",date="+date;
	}
	
}


public class Transient {
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException {
		
		//Model对象的序列化与反序列化
		System.out.println("*************** Model Serializable**********************");
		Model model = new Model("Thanple","123");
		System.out.println(model);
		
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/com/thanple/resource/object.txt"));
		out.writeObject(model);
		out.close();
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/com/thanple/resource/object.txt"));
		Model modelSerialize = (Model)in.readObject();
		System.out.println(modelSerialize);
		in.close();
		
		
		//Model2对象的序列化与反序列化
		System.out.println("*************** Model2 Externalizable**********************");
		Model2 model2 = new Model2("Thanple","123");
		System.out.println(model2);
		
		ObjectOutputStream out2 = new ObjectOutputStream(
				new FileOutputStream("src/com/thanple/resource/object2.txt"));
		out2.writeObject(model2);
		out2.close();
		
		ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("src/com/thanple/resource/object2.txt"));
		Model2 modelExternalizable = (Model2)in2.readObject();
		System.out.println(modelExternalizable);
		in2.close();
		
	}
}
