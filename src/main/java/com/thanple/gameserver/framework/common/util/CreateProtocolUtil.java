package com.thanple.gameserver.framework.common.util;

import com.thanple.gameserver.framework.common.provider.ConfigConst;
import com.thanple.thinking.root.Constant;

import java.io.*;

/**
 * Created by Thanple on 2017/1/22.
 */
public class CreateProtocolUtil {

    public static final String Protocol_Package = ConfigConst.CREATE_PROTOCOL_PACKAGE;

    /**
     * 通过执行cmd命令调用protoc.exe程序
     * protoc.exe -I=proto的输入目录 --java_out=java类输出目录 proto的输入目录包括包括proto文件
     * @param String protoFilePath proto源文件路径
     * @param String protoFile proto源文件名
     * */
    public static void createCmdProtocol(String protoFilePath , String protoFile){
        String protocExe = Constant.Path.TOOL_PROTOC;
        String strCmd = String.format("%s -I=%s --java_out=%s %s",
                protocExe,
                protoFilePath,
                Constant.Path.SRC_ROOT_PATH,
                protoFilePath+"/"+protoFile);
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

    public static void createUserProtocol(String dictionaryPath,String packagez,String className){
        File dictionary = new File(dictionaryPath);
        if(!dictionary.isDirectory())   return;
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(dictionaryPath + "/" + className + ".java")));

            StringBuilder builder = new StringBuilder();
            builder.append("package ").append(packagez).append(";\n\n");

            //注释
            builder.append("/**").append("\n");
            builder.append(" * auto created by CreateProtocolUtil").append("\n");
            builder.append(" * */").append("\n\n");

            //类
            builder.append("public class ").append(className)
                    .append(" extends ").append("\n")
                    .append("       ").append(Protocol_Package).append(".Protocol").append("\n")
                    .append("               ").append("<").append(Protocol_Package)
                    .append("._"+className).append("."+className).append(">{").append("\n\n");
            //构造函数
            builder.append("    public ").append(className).append("(").append("\n")
                    .append("           ").append(Protocol_Package)
                    .append("._"+className).append("."+className).append(" msg")
                    .append("){").append("\n");
            builder.append("        super(msg);").append("\n");
            builder.append("    }").append("\n\n");

            //process方法
            builder.append("    @Override\n");
            builder.append("    public void process(io.netty.channel.ChannelHandlerContext ctx) {\n");
            builder.append("\n");
            builder.append("    }\n");
            builder.append("}");

            writer.write(builder.toString());
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        System.out.println(Constant.Path.RUN_CLASS_PATH+"/com/thanple/thinking/protobuf"+"\n"+"TestProto.proto");
        createCmdProtocol(Constant.Path.RESOURCE_ROOT_PATH+"/com/thanple/thinking/protobuf",
                "GameServerSMsg.proto");

        createUserProtocol(Constant.Path.SRC_ROOT_PATH+"/com/thanple/gameserver/framework/app/msg",
                "com.thanple.gameserver.framework.app.msg","GameServerSMsg");
    }
}
