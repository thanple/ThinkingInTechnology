package com.thanple.thinking.genericity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sun.misc.Queue;

/*
 *	用适配器仿真潜在类型机制 
 *	AddableCollectionAdapter ： 对象适配器
 *	AdapterSimpleQueue ：类的适配器
 *
 *	结论：对象适配器更广，因为对象成员持有的是接口或基类，而类适配器只能继承，比较单一
 * */

//需要适配的接口
interface Addable<T>{	void add(T t);	}

class Fill{
	public static <T> void fill(Addable<T> addable , Class<? extends T> classToken , int size){
		for(int i=0;i<size;i++){
			try {
				addable.add(classToken.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static <T> void fill(Addable<T> addable , Generator<T> generator , int size){
		for(int i=0;i<size;i++){
			addable.add(generator.next());
		}
	}
}

class AddableCollectionAdapter<T> implements Addable<T>{	//对象适配器
	private Collection<T> c;
	
	public AddableCollectionAdapter(Collection<T> c){
		this.c = c;
	}
	@Override
	public void add(T t) {
		c.add(t);
	}
}

class Adapter{
	public static <T> Addable<T> collectionAdapter(Collection<T> c){
		return new AddableCollectionAdapter<T>(c);
	}
}

class AdapterSimpleQueue<T> extends Queue<T> implements Addable<T>{	//类的适配器
	@Override
	public void add(T t) {
		super.enqueue(t);
	}
}


public class Adapter_Addable_demo {
	
	public static void main(String[] args){
		
		List<Coffee> carrier = new ArrayList<Coffee>();
		Fill.fill(new AddableCollectionAdapter<Coffee>(carrier) , Coffee.class , 3);
		Fill.fill(Adapter.collectionAdapter(carrier), Coffee.class, 3);
		for(Coffee c : carrier)
			System.out.println(c);
		
		System.out.println("-----------------------------------");
		
		AdapterSimpleQueue<Coffee> coffeeQueue = new AdapterSimpleQueue<Coffee>();
		Fill.fill(coffeeQueue, Latte.class, 4);
		Fill.fill(coffeeQueue, Latte.class, 1);
		for(Coffee c : carrier)
			System.out.println(c);
	
		
	}
	
}
