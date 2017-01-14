package com.thanple.thinking.genericity;

import java.util.Iterator;
import java.util.Random;

/*
 *  Generator 和 Iterator接口的使用
 * */

interface Generator<T>{	T next(); }

class Coffee{
	private static long counter = 0;
	private final long id = counter++;
	@Override
	public String toString(){
		return getClass().getSimpleName()+" : "+id;
	}
}
class Latte extends Coffee{}
class Mocha extends Coffee{}
class Cappuccino extends Coffee{}
class Americano extends Coffee{}

class CoffeeGenerator implements Generator<Coffee> , Iterable<Coffee>{
	
	private Class<?>[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class };
	private static Random rand = new Random(47);
	private int size = 0;
	
	public CoffeeGenerator(){}
	public CoffeeGenerator(int sz){size = sz;}
	
	//Generator接口
	@Override
	public Coffee next() {
		try {
			return (Coffee)types[rand.nextInt(types.length)].newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Iterable接口
	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeIterator();
	}
	
	private class CoffeeIterator implements  Iterator<Coffee>{
		int count = size;
		@Override
		public boolean hasNext() {
			return count > 0;
		}
		@Override
		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();
		}	
	}
}

public class Iterator_Demo {
	
	public static void main(String[] args) {
		
		CoffeeGenerator gen = new CoffeeGenerator();
		for(int i=0;i<5;i++)
			System.out.println(gen.next());
	
		//增强型循环只认实现Iterable接口的iterator函数
		for(Coffee c : new CoffeeGenerator(5))
			System.out.println(c);
	}

}


