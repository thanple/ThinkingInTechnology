package com.thanple.thinking.jvm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 动态代理：让代理生成的一个类实现自己的一个接口 
 * */
interface IA{
	public void test();
}

class IAImpl implements IA{

	@Override
	public void test() {
		System.out.println("Test");
	}
}

class DynamicProxy implements InvocationHandler
{
    private Object subject;
    public DynamicProxy(Object subject){
        this.subject = subject;
    }
    
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        System.out.println("***************代理之前***************");  
        System.out.println("代理方法:" + method);
        
        method.invoke(subject, args);
        
        System.out.println("***************代理之后***************");
        
        return null;
    }
}

public class DynamicClassForInterface {

	public static void main(String[] args) {
		
		IA t = new IAImpl();
		InvocationHandler handler = new DynamicProxyTest(t);
		
		Class<?> c1 = Proxy.getProxyClass(
				DynamicProxyTest.class.getClassLoader(),
				IAImpl.class.getInterfaces()
			);
		
		for(Class<?> eachInterfacec : c1.getInterfaces()){
			System.out.println(eachInterfacec);
		}
		
	}
	
}
