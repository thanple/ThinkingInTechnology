package com.thanple.thinking.genericity;

class A1{	}
class B1 extends A1{}

class GenericityA<T>{
	T t;

	public void set(T t){this.t = t;}
	
	
	T test(){	  return t; }
}

public class Test {
	
	private static void test(GenericityA<A1> t){
		t.test();
	}
	private static void test2(GenericityA<? extends A1> t){
		t.test();
	}

	public static void main(String[] args) {
		GenericityA<A1> t = new GenericityA<A1>();
		t.set(new B1());

		//test(new GenericityA<B1>());	//此语句错误
		test2(new GenericityA<B1>());
	}

}
