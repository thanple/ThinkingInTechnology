package com.thanple.gameserver.framework.common.provider;

import com.thanple.gameserver.framework.common.util.CommonUtil;
import com.thanple.gameserver.framework.common.util.CreateProtocolUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanple on 2017/1/23.
 *
 * 生成所有协议文件，包括protobuf生成的文件和自定义的文件
 *
 */
public class ProtocolCreator {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        //key=>protocolName , value=>userProtocolClasName
        Map<String,String> classMap = new HashMap<>();

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(ConfigConst.USER_PROTOCOL_PROPERTIES_PATH));
        Node root = document.getFirstChild();
        NodeList protocols = root.getChildNodes();
        for(int i=0,len=protocols.getLength(); i<len; i++){
            if(protocols.item(i).getNodeName().equals("Protocol")){
                String userCls = protocols.item(i).getTextContent().trim();
                String classzName = userCls.substring(userCls.lastIndexOf(".")+1,userCls.length());

                classMap.put(classzName,userCls);

            }
        }

        File protoDic = new File(ConfigConst.DIR_PROTOBUFS);
        for (File each : protoDic.listFiles()){
            String fileName = each.getName();
            if(fileName.endsWith(".proto")){

                //通过protobuf直接生成java协议文件
                CreateProtocolUtil.createCmdProtocol(each.getParent(),each.getName());

                //生成业务协议java文件
                String fileNameWithoutPro = each.getName().substring(0,each.getName().indexOf(".proto"));
                String userCls = classMap.get(fileNameWithoutPro);

                String packagez = CommonUtil.getPackageByClassName(userCls);
                String simpleCls = CommonUtil.getSimpleNameByClassName(userCls);
                String dic = CommonUtil.getDitionaryByPackage(packagez);

                CreateProtocolUtil.createUserProtocol(ConfigConst.CREATE_PROTOCOL_PACKAGE,dic,packagez,simpleCls);

            }
        }

        System.out.println("生成完成...");
    }
}
