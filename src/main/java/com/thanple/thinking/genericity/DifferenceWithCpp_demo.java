package com.thanple.thinking.genericity;


/*
 * 泛型与C++的不同：多了边界检测
 * 1. T.f();问题
 * 2. new T();问题
 * 3. 泛型继承与接口问题
 * 4. 泛型数组
 * */

class HasF{
	void function(){
		System.out.println("HasF::function()");
	}
}

class ReturnGenericType <T extends HasF>{
	private T obj;
	public ReturnGenericType(T x){	obj = x; }
	public T get(){
		return obj;
	}
	public void exe(){
		obj.function(); 	//只有obj的类型T满足 <T extends HasF> 才可以调用此方法
	}
}

class ClassAsFactory<T>{
	private T x;
	public ClassAsFactory(Class<T> kind){
		try {
			x = kind.newInstance();		//当T类型没有默认构造函数时会报异常（如 Integer）
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public String toString(){ return String.valueOf(x); }
}

//工厂模式创建new T（）；
interface FactoryI<T>{ T create(); }

class IntegerFactory implements FactoryI<Integer>{
	@Override
	public Integer create() {
		return new Integer(0);
	}
}
class Widget{
	public static class Factory implements FactoryI<Widget>{
		@Override
		public Widget create() {
			return new Widget();
		}
	}
}

class Foo2<T>{ 
	private T x; 
	public  <F extends FactoryI<T>> Foo2(F factory){
		x = factory.create();
	}
	public String toString(){	return String.valueOf(x);	}
}


//泛型继承与接口
interface A{}
class B{}
class C <T extends B & A> {
	T item;
}

//泛型数组
class Generic <T>{}

class ArrayOfGenericReference<T>{
	private Object[] array;
	public ArrayOfGenericReference(int sz){ array = new Object[sz];	}
	
	public void put(int index , T item){
		array[index] = item;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index){ return (T)array[index]; }
	
}

public class DifferenceWithCpp_demo {

	public static void main(String[] args) {
		
		//T.f()
		ReturnGenericType<HasF> f = new ReturnGenericType<HasF>(new HasF());
		f.exe();
		
		//new T
		ClassAsFactory<HasF> fe = new ClassAsFactory<HasF>(HasF.class);
		System.out.println(fe);
		
		//new T : factory
		System.out.println( new Foo2<Integer>(new IntegerFactory()) );
		System.out.println( new Foo2<Widget>(new Widget.Factory()) );
		
		//泛型数组
		ArrayOfGenericReference<Integer> gai = new ArrayOfGenericReference<Integer>(5);
		for(int i=0;i<5;i++)
			gai.put(i, i);
		for(int i=0;i<5;i++){
			Integer in = gai.get(i);	//能复制说明是Integer类型
			System.out.print(in+" ");
		}
			
	}

}
