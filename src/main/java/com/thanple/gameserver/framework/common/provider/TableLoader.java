package com.thanple.gameserver.framework.common.provider;


import com.thanple.gameserver.framework.common.berkeleydb.table.TTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thanple on 2017/1/17.
 * 表的加载器，但是在BerkeleyDB里是数据库的加载器，我们习惯称为“表加载器”
 */
public class TableLoader {

    private static List<String> tableNames = new ArrayList<>();

    /**
     * 从TTable所在目录下获取所有的表
     * */
    static {
        File dictionary = new File(ConfigConst.TABLE_DIR);
        for(File file : dictionary.listFiles()){
            String name = file.getName();
            if(name.endsWith(".class") && !name.equals("TTable.class")){
                tableNames.add(name.substring(0,name.indexOf(".class")));
            }
        }
    }

    public static List<String> getTableNames() {
        return tableNames;
    }



    /**
     * key:表名类 value:TTable
     * 享元模式
     * */
    private static Map<Class,TTable> tables = new HashMap<>();

    public static void putTTable(TTable tTable){
        tables.put(tTable.getClass(),tTable);
    }

    public static Map<Class, TTable> getTables() {
        return tables;
    }

    /**
     * 获取TTable实例
     * TTable在Map<Class,tables>享元
     * @param Class<TB> tableClass
     * @return TB
     * */
    public static <TB extends TTable> TB getTableInstance(Class<TB> tableClass){
        return (TB)tables.get(tableClass);
    }
}
