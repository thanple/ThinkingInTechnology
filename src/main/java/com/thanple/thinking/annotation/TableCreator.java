package com.thanple.thinking.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DBTable{
	public String name() default "";
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Constraints{
	boolean primaryKey() default false;
	boolean allowNull() default true;
	boolean unique() default false;
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLString{
	int value() default 0;
	String name() default "";
	Constraints constraints() default @Constraints;
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLInteger{
	String name() default "";
	Constraints constraints() default @Constraints;
}

@DBTable(name="MEMBER")
class Member{
	@SQLString(30) String firstName;
	@SQLString(50) String lastName;
	@SQLInteger Integer age;
	@SQLString(value=30 , constraints=@Constraints(primaryKey=true)) String handle;
	
	static int memberCount;
	public Integer getAge() {
		return age;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getHandle() {
		return handle;
	}
	public String getLastName() {
		return lastName;
	}
	public String toString(){
		return handle;
	}
}

public class TableCreator {

	public static void main(String[] args) throws ClassNotFoundException {
		
		String className = "com.thanple.thinking.se.annotation.Member";
		Class <?> cl = Class.forName(className);
		DBTable dbTable = cl.getAnnotation(DBTable.class);
		if(dbTable==null){
			System.out.println("No DBTable annotations in class "+className);
			System.exit(0);
		}
		
		String tableName = dbTable.name();
		if(tableName.length() < 1)
			tableName = cl.getName().toUpperCase();
		List<String> columnDefs = new ArrayList<String>();
		for(Field field : cl.getDeclaredFields()){
			
			String columnName = null;
			Annotation [] anns = field.getDeclaredAnnotations();
			if(anns.length < 1)
				continue;
			
			
			if(anns[0] instanceof SQLInteger){
				SQLInteger sInt = (SQLInteger)anns[0];
				
				if(sInt.name().length() < 1)
					columnName = sInt.name().toUpperCase();
				else
					columnName = sInt.name();
				columnDefs.add(columnName + " INT" + getConstranints(sInt.constraints()));
			}
			if(anns[0] instanceof SQLString){
				SQLString sString = (SQLString)anns[0];
				
				if(sString.name().length() < 1)
					columnName = sString.name().toUpperCase();
				else
					columnName = sString.name();
				columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")"+
						getConstranints(sString.constraints()));
			}		
		}
		
		StringBuilder createCommand = new StringBuilder("CREATE TABLE "+tableName+"(");
		for(String columnDef : columnDefs)
			createCommand.append("\n   "+columnDef+",");
		
		
		String tableCreate = createCommand.substring(0 ,createCommand.length()-1) + ");";
		
		System.out.println("Table Creation SQL for "+ className + " is :\n" +tableCreate);
	}
	
	private static String getConstranints(Constraints con){
		String constrints = "";
		if(!con.allowNull())
			constrints += " NOT NULL";
		if(con.primaryKey())
			constrints += " PRIMARY KEY";
		if(con.unique())
			constrints += " UNIQUE";
		
		return constrints;
	}

}
