package com.thanple.thinking.jvm;

import java.util.HashSet;
import java.util.Set;

/**
 * HashCode的研究
 * 
 * 如果我们将对象的属性值参与了hashCode的运算中，在进行删除的时候，
 * 就不能对其属性值进行修改，否则会出现严重的问题。
 * */
class People{
    protected String name;
    protected int age;
     
    public People(String name,int age) {
        this.name = name;
        this.age = age;
    }  
     
    public void setAge(int age){
        this.age = age;
    }
         
    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
    }    
}

class DevirsePeople extends People{
	public DevirsePeople(String name, int age) {
		super(name, age);
	}

	@Override
    public int hashCode() {
        return name.hashCode()*37+age;
    }
}

 
class RectObject {  
    public int x;  
    public int y;  
    public RectObject(int x,int y){  
        this.x = x;  
        this.y = y;  
    }  
    @Override  
    public int hashCode(){  
        final int prime = 31;  
        int result = 1;  
        result = prime * result + x;  
        result = prime * result + y;  
        return result;  
    }  
    @Override  
    public boolean equals(Object obj){  
        if(this == obj)  
            return true;  
        if(obj == null)  
            return false;  
        if(getClass() != obj.getClass())  
            return false;  
        final RectObject other = (RectObject)obj;  
        if(x != other.x){  
            return false;  
        }  
        if(y != other.y){  
            return false;  
        }  
        return true;  
    }  
}  

public class HashCode_demo {
	
	public static void main(String[] args) {
		
		People p1 = new People("Jack", 12),
				p2 = new People("Jack", 12);
		System.out.println(p1.equals(p2));
		
		//两个对象
		Set<People> peoples = new HashSet<People>();
		peoples.add(p1);
		peoples.add(p2);
        System.out.println(peoples);
        
        
        DevirsePeople p3 = new DevirsePeople("Tom", 11),
        			p4 = new DevirsePeople("Tom", 11);     
        System.out.println(p3.equals(p4));
		
		//一个对象
		Set<DevirsePeople> devirsePeoples = new HashSet<DevirsePeople>();
		devirsePeoples.add(p3);
		devirsePeoples.add(p4);
        System.out.println(devirsePeoples);
        
        
        //内存泄露问题
        HashSet<RectObject> set = new HashSet<RectObject>();  
        RectObject r1 = new RectObject(3,3);  
        RectObject r2 = new RectObject(5,5);  
        RectObject r3 = new RectObject(3,3);  
        set.add(r1);  
        set.add(r2);  
        set.add(r3);  
        r2.y = 7;  
        System.out.println("删除前的大小size:"+set.size());  
        set.remove(r2);
        System.out.println("删除后的大小size:"+set.size());  
		
	}

}
