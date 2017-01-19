package com.thanple.thinking.util;


import com.thanple.thinking.root.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String strCmd = String.format("%s -I=%s --java_out=%s %s",
                protocExe,protoFilePath,Constant.Path.SRC_ROOT_PATH, protoFilePath+"/"+protoFile);
        //"d:/dev/protobuf-master/src/protoc.exe -I=./proto --java_out=./src/main/java ./proto/"+ protoFile;

        try {
            Process process = Runtime.getRuntime().exec(strCmd);

            //打印结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str;
            while ((str = reader.readLine())!=null){
                System.out.println(str);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        System.out.println(Constant.Path.RUN_CLASS_PATH+"/com/thanple/thinking/protobuf"+"\n"+"TestProto.proto");
        createJavaFile(Constant.Path.RESOURCE_ROOT_PATH+"/com/thanple/thinking/protobuf", "TestProto.proto");
    }
}
