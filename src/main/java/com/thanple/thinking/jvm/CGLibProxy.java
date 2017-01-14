package com.thanple.thinking.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/**
 * 
 * 无需实现接口也可实现动态代理：
 * 可以自定义创建实现一个动态接口的动态对象。
 * */

class SayHello {
	public void say(){
	  System.out.println("hello everyone");
	}
}

public class CGLibProxy implements MethodInterceptor {

	public static void main(String[] args) {
		CGLibProxy proxy = new CGLibProxy();
		
		//通过生成子类的方式创建代理类
		SayHello proxyImp = (SayHello)proxy.getProxy(SayHello.class);	//SayHello换成接口也一样
		proxyImp.say();
		
		System.out.println(proxyImp);
	}
	
	//动态生成对象
	public Object getProxy(Class<?> clazz){	
		Enhancer enhancer = new Enhancer();
		//设置需要创建子类的类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		//通过字节码技术动态创建子类实例
		return enhancer.create();
	 }
	 
	//实现MethodInterceptor接口方法
	@Override
	 public Object intercept(Object obj, Method method, Object[] args,
		  MethodProxy proxy) throws Throwable {
		 
		  System.out.println("前置代理");
		  
		  //通过代理类调用父类中的方法
		  Object result = proxy.invokeSuper(obj, args);
		  
		  System.out.println("后置代理");
		  return result;
	 }

}
