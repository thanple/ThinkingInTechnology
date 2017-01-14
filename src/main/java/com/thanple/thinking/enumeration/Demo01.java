package com.thanple.thinking.enumeration;

import java.util.Random;

/*
 * 总结：
 * 1.枚举类型是以0为开始的一系列自然数
 * 2.枚举类型变量只能表示枚举类型里面的一个值
 * 3.通过重写toString()方法可以让每个对象得到自定义的字符串构造（比如自定义输出）
 * 4.enum类型只能implements而不能extends，因为所有的enum都继承自java.lang.Enum
 * 5.values()代表enum所有情况的集合
 * */

interface Generator<T> {
	public T next();
}
public class Demo01 {

		//枚举类型
		public enum Shrubbery{GROUND,CRWALING,HANGING};
		
		Shrubbery shrub;	//枚举类型的变量
		
		public Demo01(Shrubbery shrub){
			this.shrub = shrub;
		}
		//重写toString()方法方便输出
		@Override
		public 	String toString(){
			return "Shrubbery is "+shrub;
		}
		
		//实现接口的enum
		enum CartoonCharacter implements Generator<CartoonCharacter>{
			SLAPPY,SPANKY,PUNCHY,SILLY,BOUNDCY,NUTTY,BOB;
			
			private Random rand = new Random(47);
			
			@Override
			public CartoonCharacter next(){
				return values()[rand.nextInt(values().length)];
			}
		};

		private static <T> void printNext(Generator<T> rg){
			System.out.print(rg.next()+" ");
		}
		public static void main(String[] args) {
			
			//测试compareTo与enum的各值
			for(Shrubbery each:Shrubbery.values()){
				System.out.print(each.compareTo(Shrubbery.GROUND)+" ");
				System.out.print(each.compareTo(Shrubbery.CRWALING)+" ");
				System.out.println(each.compareTo(Shrubbery.HANGING));
			}
			
			//输出各枚举类型(用重载后的toString)
			System.out.println(new Demo01(Shrubbery.GROUND));
			System.out.println(new Demo01(Shrubbery.CRWALING));
			System.out.println(new Demo01(Shrubbery.HANGING));
			
			//enum只能实现而不能继承测试
			CartoonCharacter  cc = CartoonCharacter.BOB;
			for(int i=0;i<10;i++)
				printNext(cc);
			
		}

}
