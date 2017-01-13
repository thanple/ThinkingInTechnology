package com.thanple.thinking.root;

import java.io.File;

/**
 * Created by Thanple on 2016/12/15.
 */
public class PathTest {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(PathTest.class.getClassLoader().getResource(""));
        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println(PathTest.class.getResource(""));
        System.out.println(PathTest.class.getResource("/")); //Class文件所在路径
        System.out.println(new File("/").getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
    }
}
