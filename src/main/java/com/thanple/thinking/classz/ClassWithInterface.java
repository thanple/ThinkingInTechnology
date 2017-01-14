package com.thanple.thinking.classz;


/*
 * class对象访问基类class和接口class
 * */

interface HasBatteries{}
interface Waterproot{}
interface Shoots{}

class Toy{
	Toy(){}
	Toy(int i){}
}

class FancyToy extends Toy implements HasBatteries,Waterproot,Shoots{
	FancyToy(){ super(1); }
}

public class ClassWithInterface {
	
	public static void main(String[] args) {
		Class<?> c = null;
		try {
			c = Class.forName("com.thanple.thinking.se.Class.FancyToy");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		printInfo(c);
		
		//获取接口class
		for(Class<?> face : c.getInterfaces()){
			printInfo(face);
		}
		
		//获取基类class
		Class<?> up = c.getSuperclass();
		
		Object obj = null;
		
		try {
			obj = up.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		printInfo(obj.getClass());
		
	}

	static void printInfo(Class<?> cc){
		System.out.println("Class name: "+cc.getName()+" is interface?["+cc.isInterface()+"]");
	}
}
