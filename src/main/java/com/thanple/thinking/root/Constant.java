package com.thanple.thinking.root;

/**
 * Created by Thanple on 2016/12/15.
 * 工程的常量配置文件
 */
public class Constant {

    public static class Path{

        //工程根目录
        //F:\IdeaProjects\ThinkingInWorld
        public static final String ROOT_PATH = System.getProperty("user.dir");

        //运行class目录
        ///F:/IdeaProjects/ThinkingInWorld/target/classes/
        public static final String RUN_CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

}
