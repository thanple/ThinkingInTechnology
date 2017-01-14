package com.thanple.thinking.genericity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("serial")
/*
 * 适配器方法管用法
 * */

class ReversibleArrayList<T> extends ArrayList<T>{
	public ReversibleArrayList(Collection<T> c){ super(c); }
	
	public Iterable<T> reversed(){
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					int current = size() -1;
					@Override
					public boolean hasNext() {
						return current > -1;
					}
					@Override
					public T next() {
						return get(current--);
					}					
				};
			}
			
		};
	}
}

public class Adapter_demo {

	public static void main(String[] args) {
		ReversibleArrayList<String> ral = new ReversibleArrayList<String>(
				Arrays.asList("To be or not to be".split(" ")));
		
		for(String s : ral.reversed())
			System.out.println(s);
	

	}

}
