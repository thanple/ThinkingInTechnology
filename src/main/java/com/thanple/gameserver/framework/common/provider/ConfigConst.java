package com.thanple.gameserver.framework.common.provider;


import com.thanple.gameserver.framework.common.berkeleydb.table.TTable;
import com.thanple.thinking.root.Constant;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Thanple on 2017/1/17.
 */
public class ConfigConst {
    public static final String DB_DATA_PATH = "berkeley-db-data";
    public static final boolean ALLOW_DUPLICATED_KEY = false;  //是否允许一个key对应多个value
    public static final boolean TRANSACTION_ON_OFF = true;  //事务开关
    public static final boolean ALLOW_CREATE = true;    //如果不存在则自动创建

    public static final String TABLE_PACKAGE = TTable.class.getPackage().getName();
    public static URI TABLE_DIR = null;
    static {
        try {
            TABLE_DIR = TTable.class.getResource("").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //生成的协议文件包
    public static final String CREATE_PROTOCOL_PACKAGE = "com.thanple.gameserver.framework.common.nio.protocol";

    //生成的协议文件夹
    public static final String CREATE_PROTOCOL_PROPERTIES_PATH =
            Constant.Path.RESOURCE_ROOT_PATH + "/com/thanple/gameserver/framework/msg.protocol.create.properties";

    //业务协议文件夹
    public static final String USER_PROTOCOL_PROPERTIES_PATH =
            Constant.Path.RESOURCE_ROOT_PATH + "/com/thanple/gameserver/framework/msg.protocol.user.properties";

    //protobuf脚本所在文件夹
    public static final String DIR_PROTOBUF =
            Constant.Path.RESOURCE_ROOT_PATH + "/com/thanple/gameserver/framework/protobufs";

}
