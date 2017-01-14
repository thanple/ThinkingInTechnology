package com.thanple.thinking.enumeration;

/**
 * 所有的枚举值都是类静态常量，在初始化时会对所有的枚举值对象进行第一次初始化。
 * 枚举类在后台实现时，实际上是转化为一个继承了java.lang.Enum类的实体类，
 * 原先的枚举类型变成对应的实体类型，上例中AccountType变成了个class AccountType，
 * 并且会生成一个新的构造函数，若原来有构造函数，则在此基础上添加两个参数，生成新的构造函数，
 * 如上例子中：
	private AccountType(){ System.out.println(“It is a account type”); }
	会变成：

	private AccountType(String s, int i){
    	super(s,i); System.out.println(“It is a account type”); }
    	
       而在这个类中，会添加一段static代码段：
	static{
    AccountType SAVING = new AccountType("SAVING", 0);
    AccountType FIXED = new AccountType("FIXED", 1);
    AccountType CURRENT = new AccountType("CURRENT", 2);
   $VALUES = new AccountType[]{
         SAVING, FIXED, CURRENT
    } }
 * 
 * */
interface Operation{
	double apply(double x, double y);
}

enum ExtendedOperation implements Operation{
	EXP("^") {
		@Override
		public double apply(double x, double y) {
			return Math.pow(x, y);
		}
	},
	REMAINDER("%"){
		@Override
		public double apply(double x, double y) {
			return x%y;
		}
	};

	private final String symbol;
	ExtendedOperation(String symbol){
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return "'"+symbol+"'";
	}
}


public class EnumWithInterface {
	EnumWithInterface(){}
	public static void main(String[] args) {
		
		double x = 39.2;
		double y = 5.1;
	
		for(Operation op : ExtendedOperation.class.getEnumConstants()){
			System.out.printf("%f %s %f = %f%n",x,op,y,op.apply(x, y));
		}
	}

}
	
