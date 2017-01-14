package com.thanple.thinking.genericity;


/*
 * 自限制泛型
 * */


//接口自限制泛型
interface SelfBround < T extends SelfBround<T> >{
	T get();
	void set(T arg);
}
interface Setter extends SelfBround<Setter>{}

//类的自限制泛型
class Base{}
class Derived extends Base{}

class GenericSetter<T>{
	void set(T arg){	System.out.println("GenericSetter"); }
}
class DerivedGS extends GenericSetter<Base>{
	void set(Derived arg){	System.out.println("DerivedGS"); }	//重载而非覆盖
}

class GenericSetter2<T extends GenericSetter2<T>>{
	void set(T arg){	System.out.println("GenericSetter2"); }
}
class DerivedGS2 extends GenericSetter2<DerivedGS2>{
	void set(Derived arg){	System.out.println("DerivedGS2"); }
}



public class SelfBounded_demo {
	
	public static void test(Setter s1 , Setter s2 , SelfBround<Setter> sb){
		s1.set(s2);
		//s1.set(sb);	//编译出错，有限定
	}

	public static void main(String[] args) {

		//类的自限制泛型
		Base base = new Base();
		Derived derived = new Derived();
		
		DerivedGS dgs = new DerivedGS();
		dgs.set(base);		//重载，而非覆盖
		dgs.set(derived);	
		
		DerivedGS2 dgs2 = new DerivedGS2();
		//dgs2.set(base);	//不支持重载，只有覆盖
		dgs2.set(derived);
	}

}
