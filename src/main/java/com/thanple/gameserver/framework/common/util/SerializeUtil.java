package com.thanple.gameserver.framework.common.util;

import java.io.*;

/**
 * Created by Thanple on 2017/1/13.
 * 序列化工具
 */
public class SerializeUtil {

    public static<T extends Serializable> byte[] serialize(T object) {
        if(null == object)   return null;

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Serialize Object Filed:"+e.getMessage());
        }

        return bytes;
    }

    public static <T extends Serializable> T unserialize(byte[] bytes) {

        if(null == bytes)   return null;

        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Object result = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            result = ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("UnSerialize Object Filed: "+e.getMessage());
        }

        return (T)result;
    }
}
