package com.thanple.thinking.genericity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;


/*
 * Generator的一个demo
 * */

class  Customer{
	private static long counter = 1;
	private final long id = counter++;
	private Customer(){}
	public String toString(){	return "Customer "+id;	}
	
	public static Generator<Customer> generator(){
		return new Generator<Customer>(){
			@Override
			public Customer next() {
				return new Customer();
			}
		};
	}
}

class Generators{
	public static <T> Collection<T> fill(Collection<T> coll , Generator<T> gen ,int n){
		for(int i=0;i<n;i++)
			coll.add(gen.next());
		return coll;
	}
}

public class Generator_demo {

	public static void main(String[] args) {
		
		Queue<Customer> line = new LinkedList<Customer>();
		
		Generators.fill(line, Customer.generator(), 15);

		System.out.println(line);
	}

}
