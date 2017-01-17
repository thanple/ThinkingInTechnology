package com.thanple.thinking.berkeleyDB.framework.dataprovider;

import com.thanple.thinking.berkeleyDB.framework.table.TTable;

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
}
