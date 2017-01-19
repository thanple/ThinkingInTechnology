package com.thanple.thinking.util;


import com.thanple.thinking.root.Constant;

import java.io.IOException;

/**
 * Created by Thanple on 2017/1/19.
 * Protobuf生成Java的工具
 */
public class ProtoCreateTool {

    /**
     * 通过执行cmd命令调用protoc.exe程序
     * protoc.exe -I=proto的输入目录 --java_out=java类输出目录 proto的输入目录包括包括proto文件
     * @param String protoFilePath proto源文件路径
     * @param String protoFile proto源文件名
     * */
    public static void createJavaFile(String protoFilePath , String protoFile){
        String protocExe = Constant.Path.TOOL_PROTOC;
        String strCmd = String.format("%s -I=%s --java_out=%s %s",protocExe,protoFilePath,Constant.Path.SRC_ROOT_PATH, protoFilePath+"/"+protoFile);
        //"d:/dev/protobuf-master/src/protoc.exe -I=./proto --java_out=./src/main/java ./proto/"+ protoFile;

        try {
            Process process = Runtime.getRuntime().exec(strCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createJavaFile(Constant.Path.RUN_CLASS_PATH+"/com/thanple/thinking/protobuf", "TestProto.proto");
    }
}
