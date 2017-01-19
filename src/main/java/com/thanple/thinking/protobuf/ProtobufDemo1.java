package com.thanple.thinking.protobuf;

import com.thanple.thinking.root.Constant;

import java.io.*;

/**
 * Created by Thanple on 2017/1/19.
 *
 *  下载protobuf-2.6.1.zip和protoc-2.6.1-win32.zip，地址：https://github.com/google/protobuf/tags
 *  教程：http://shift-alt-ctrl.iteye.com/blog/2210885
 *
 * protobuf------java
 * double 	    double
 * float 	    float
 * int32 	    int
 * int64 	    long
 * bool 	    boolean
 * string 	    String
 * bytes 	    ByteString
 */
public class ProtobufDemo1 {

    public static void main(String[] args) throws IOException {

        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        personBuilder.setEmail("test@gmail.com");
        personBuilder.setId(1000);

        PersonProtos.Person.PhoneNumber.Builder phone = PersonProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");

        personBuilder.setName("张三");
        personBuilder.addPhone(phone);

        PersonProtos.Person person = personBuilder.build();
        System.out.println(person);



        //第一种方式
        //序列化
        byte[] data = person.toByteArray();//获取字节数组，适用于SOCKET或者保存在磁盘。
        //反序列化
        PersonProtos.Person result = PersonProtos.Person.parseFrom(data);
        System.out.println(result.getEmail());



        //第二种序列化：粘包,将一个或者多个protobuf对象字节写入stream。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //生成一个由：[字节长度][字节数据]组成的package。特别适合RPC场景
        person.writeDelimitedTo(byteArrayOutputStream);
        //反序列化，从steam中读取一个或者多个protobuf字节对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        result = PersonProtos.Person.parseDelimitedFrom(byteArrayInputStream);
        System.out.println(result.getEmail());


        //第三种序列化,写入文件或者Socket
        String fileName = Constant.Path.RUN_CLASS_PATH+"/test.dt";
        FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
        person.writeTo(fileOutputStream);
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(new File(fileName));
        result = PersonProtos.Person.parseFrom(fileInputStream);
        System.out.println(result.getEmail());

    }
}
