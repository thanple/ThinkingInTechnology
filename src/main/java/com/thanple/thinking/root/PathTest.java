package com.thanple.thinking.root;

import java.io.File;

/**
 * Created by Thanple on 2016/12/15.
 */
public class PathTest {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        System.out.println(PathTest.class.getClassLoader().getResource("").getPath());
        System.out.println(ClassLoader.getSystemResource("").getFile());
        System.out.println(PathTest.class.getResource("").getPath());
        System.out.println(PathTest.class.getResource("/").getPath()); //Class文件所在路径
        System.out.println(new File("/").getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
    }
}
