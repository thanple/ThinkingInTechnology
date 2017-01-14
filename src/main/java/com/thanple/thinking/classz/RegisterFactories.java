package com.thanple.thinking.classz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 * 注册工厂：抽象工厂与内部类的一个应用
 * */

interface Factory<T>{
	T create();
}

class Part{
	@Override
	public String toString(){
		return getClass().getSimpleName();
	}
	static List< Factory<? extends Part> > partFactories = new ArrayList<Factory<? extends Part>>();
	static{
		partFactories.add(new FuelFilter.FactoryInner());
		partFactories.add(new AirFilter.FactoryInner());
		partFactories.add(new OilFilter.FactoryInner());
	}
	private static Random rand = new Random(47);
	public static Part createRandom(){
		int n = rand.nextInt( partFactories.size() );
		return partFactories.get(n).create();
	}
}

class Filter extends Part{}

class FuelFilter extends Filter{
	public static class FactoryInner implements Factory<FuelFilter>{
		@Override
		public FuelFilter create() {
			return new FuelFilter();
		}	
	}
}
class AirFilter extends Filter{
	public static class FactoryInner implements Factory<AirFilter>{
		@Override
		public AirFilter create() {
			return new AirFilter();
		}	
	}
}
class OilFilter extends Filter{
	public static class FactoryInner implements Factory<OilFilter>{
		@Override
		public OilFilter create() {
			return new OilFilter();
		}	
	}
}


public class RegisterFactories {

	public static void main(String[] args) {
		for(int i=0;i<10;i++)
			System.out.println(Part.createRandom());
	}

}
