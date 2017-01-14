package com.thanple.thinking.annotation;

import java.awt.Button;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;


/*
 * 该工具是模拟安卓Activity初始化组件的一个注解工具
 * */

@Target({ ElementType.FIELD })  
@Retention(RetentionPolicy.RUNTIME)  
 @interface InjectView {  
    public int value();  
} 

class R{	static class id{	final static int button1 = 1; } }

abstract class Activity{
	public Activity(){	onCreate(); }
	Object findViewById(int id){ return new Button("Button"+String.valueOf(id)); }
	public abstract void onCreate();
}

class MyActivity extends Activity{
	@InjectView(R.id.button1)
	private Button button1;

	@Override
	public void onCreate() {	
		Injector.get(this).inject();
		System.out.println(button1);
	}	
}

/* 注解扫描工具 */
final class Injector {
    private final Activity mActivity;

    private Injector(Activity activity) {
        mActivity = activity;
    }
    public static Injector get(Activity activity) {
        return new Injector(activity);
    }
    public void inject() {
        for (Field field : mActivity.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().equals(InjectView.class)) {
                    try {
                        Class<?> fieldType = field.getType();
                        int idValue = InjectView.class.cast(annotation).value();
                        field.setAccessible(true);
                        Object injectedValue = fieldType.cast(mActivity.findViewById(idValue));
                        if (injectedValue == null) {
                            throw new IllegalStateException("findViewById(" + idValue
                                    + ") gave null for " +
                                    field + ", can't inject");
                        }
                        field.set(mActivity, injectedValue);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }
}


public class InjectorUtil {

	public static void main(String[] args) {
		new MyActivity();
	}

}
