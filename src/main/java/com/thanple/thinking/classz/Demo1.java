package com.thanple.thinking.classz;

/**
 * Created by Thanple on 2016/12/21.
 */
public class Demo1 {

    public static int num ;
    public static int num2 = 10;
    public static final String str;
    static {
        num = 5;
        num2 = 5;
        str = "Demo";



    }


    public static void main(String[] args) {

        Demo1 demo1 = (Demo1)null;
        System.out.println(demo1.num);
        System.out.println(demo1.num2);
        System.out.println(demo1.str);

    }

}
