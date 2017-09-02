package com.thanple.hadoop.common;

import org.apache.hadoop.fs.Path;

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
        // /F:/IdeaProjects/ThinkingInWorld/target/classes/
        public static final String RUN_CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        //tools目录
        public static final String TOOLS_PATH = ROOT_PATH + "/tools";
        public static final String TOOL_PROTOC = TOOLS_PATH + "/protoc.exe";

        //src目录下
        public static final String SRC_ROOT_PATH = ROOT_PATH + "/src/main/java";

        //resource目录下
        public static final String RESOURCE_ROOT_PATH = ROOT_PATH + "/src/main/resources/com/thanple/hadoop";

        //Jar名字
        public static final String JAR_NAME = ROOT_PATH+"/target/artifacts/Main/Thanple.jar";
    }

    public static org.apache.hadoop.fs.Path getLocalPath(String absPath){
        return new org.apache.hadoop.fs.Path("file://"+ Path.RESOURCE_ROOT_PATH +absPath);
    }

}
