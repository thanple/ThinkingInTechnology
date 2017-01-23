package com.thanple.gameserver.framework.common.provider;

import com.google.protobuf.MessageLite;
import lombok.Data;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Thanple on 2017/1/23.
 */
public class ProtocolLoader {

    private static ProtocolLoader instance = new ProtocolLoader();

    private ProtocolLoader(){}

    public static ProtocolLoader getInstance(){
        return instance;
    }

    //protobuf生成的协议文件属性加载
    @Data
    public static class Protocol {
        private int id;
        Class<? extends com.thanple.gameserver.framework.common.nio.protocol.Protocol> user;    //自定义的业务协议类
        Class<? extends com.google.protobuf.MessageLite> create;    //protobuf直接生成的协议类
    }

    @Getter
    private Map<Integer,Protocol> protocolMap = new HashMap<>();    //id=>protocol
    @Getter
    private Map<String,Integer> classProtocolmap = new HashMap<>();   //ProtocolName=>id


    public Protocol getProtocolByName(String simpleClassName){
        Integer id = classProtocolmap.get(simpleClassName);
        return protocolMap.get(id);
    }


    public ProtocolLoader init() throws IOException, ParserConfigurationException, SAXException {
        protocolMap.clear();
        classProtocolmap.clear();

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(ConfigConst.USER_PROTOCOL_PROPERTIES_PATH));
        Node root = document.getFirstChild();
        NodeList protocols = root.getChildNodes();
        for(int i=0,len=protocols.getLength(),protocolNum=ConfigConst.Protocol_Type_Start_Num; i<len; i++){
            if(protocols.item(i).getNodeName().equals("Protocol")){
                String userCls = protocols.item(i).getTextContent().trim();
                String classzName = userCls.substring(userCls.lastIndexOf(".")+1,userCls.length());

                Protocol protocol = new Protocol();
                protocol.setId(i);
                try {
                    protocol.setCreate((Class<com.google.protobuf.MessageLite>)Class.forName(ConfigConst.CREATE_PROTOCOL_PACKAGE+"._"+classzName+"$"+classzName));
                    protocol.setUser((Class<com.thanple.gameserver.framework.common.nio.protocol.Protocol>)Class.forName(userCls));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                protocolMap.put(protocolNum,protocol);
                classProtocolmap.put(protocol.getCreate().getSimpleName(),protocolNum);
                protocolNum++;
            }
        }

        return this;
    }


    /**
     * 反射静态内部类必须是以$,而不是.
     * */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        try {
            Class classz = Class.forName("com.thanple.gameserver.framework.common.nio.protocol._CPerson1$CPerson1");
            System.out.println(classz.getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
