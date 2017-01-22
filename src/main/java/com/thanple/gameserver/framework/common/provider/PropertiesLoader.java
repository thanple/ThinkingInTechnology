package com.thanple.gameserver.framework.common.provider;

import com.thanple.gameserver.framework.common.nio.protocol.Protocol;
import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Thanple on 2017/1/23.
 */
public class PropertiesLoader {

    private static PropertiesLoader instance = new PropertiesLoader();

    private PropertiesLoader(){}

    public static PropertiesLoader getInstance(){
        return instance;
    }

    //protobuf生成的协议文件属性加载
    @Data
    public static class Protocol {
        private int id;
        Class<? extends com.thanple.gameserver.framework.common.nio.protocol.Protocol> user;
        Class<? extends com.thanple.gameserver.framework.common.nio.protocol.Protocol> create;
    }

    Map<Integer,Protocol> protocolMap = new HashMap<>();


    public void init() throws IOException {
        Properties createProtocol = new Properties();
        createProtocol.load(new FileInputStream(
                new File(ConfigConst.CREATE_PROTOCOL_PROPERTIES_PATH)));

        Properties userProtocol = new Properties();
        createProtocol.load(new FileInputStream(
                new File(ConfigConst.USER_PROTOCOL_PROPERTIES_PATH)));

        for(Object id : createProtocol.keySet()){
            Object className = createProtocol.get(id);
            Object userClass = userProtocol.get(className);

            Protocol protocol = new Protocol();
            protocol.setId((Integer) id);
            protocol.setCreate((Class<com.thanple.gameserver.framework.common.nio.protocol.Protocol>)className);
            protocol.setUser((Class<com.thanple.gameserver.framework.common.nio.protocol.Protocol>)userClass);

        }

    }

}
