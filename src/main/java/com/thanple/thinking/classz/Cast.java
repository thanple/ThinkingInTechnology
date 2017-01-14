package com.thanple.thinking.classz;

import java.util.ArrayList;
import java.util.List;


/*
 * Class转换
 * */

class Base{
	@Override
	public String toString(){
		return "Base";
	}
}

class Derive extends Base{
	@Override
	public String toString(){
		return "Derive";
	}
}
	
public class Cast {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		//类型转换
		Class<Derive> c = null;
		try {
			c = (Class<Derive>) Class.forName("com.thanple.thinking.se.Class.Derive");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(c);
		
		Base base = null;
		try {
			base = (Base) c.getSuperclass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		if(c.getSuperclass().isInstance(base)){
			System.out.println("isInstance");
		}
		
							
		//泛型不支持多态，但是支持通配泛型
		List<Class<? extends Base>> list = new ArrayList<Class<? extends Base>>();
		list.add(c);
		list.add((Class<Base>) c.getSuperclass());
		System.out.println(list);
		
		
		Class<? extends Base> s = c;	//Class<Base> s = c; 是错的
		List<Base> l = new ArrayList<Base>();
		l.add(new Derive());					//这种情况是可以的，只是多态的一个变体
		
	}

}
