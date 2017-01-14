package com.thanple.thinking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface UserCase {
	public int id();
	public String description() default "no description";
}

class PasswordUtils{
	@UserCase(id=47,description="Passwords must contaions at least one numeric")
	public boolean validatePassword(String password){
		return (password.matches("\\w*\\d\\w*"));
	}
	
	@UserCase(id=48)
	public String encryptPassword(String password){
		return new StringBuilder(password).reverse().toString();
	}
	
	@UserCase(id=49,description="New passwords can't equal previously used ones")
	public boolean checkForNewPassword(List<String> previousPasswords ,String password){
		return !previousPasswords.contains(password);
	}
}

public class demo01 {
	
	public static void trackUserCases(List<Integer> userCases , Class<?> c1){
		for(Method m : c1.getDeclaredMethods()){
			UserCase uc = m.getAnnotation(UserCase.class);
			if(uc!=null){
				System.out.println("Found User Case:"+uc.id()+" "+uc.description());
				userCases.remove(new Integer(uc.id()));
			}
		}
		for(int i : userCases){
			System.out.println("Warning : Missing user case-"+i);
		}
	}

	public static void main(String[] args) {
		
		List<Integer> userCases = new ArrayList<Integer>();
		Collections.addAll(userCases, 47,48,49);
		trackUserCases(userCases, PasswordUtils.class);
	}

}
