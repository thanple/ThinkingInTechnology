package com.thanple.thinking.berkeleyDB.framework.dataprovider;

import com.thanple.thinking.berkeleyDB.framework.table.TTable;

import java.io.File;
import java.net.URISyntaxException;
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

    //此处加载所有表的名字
    static {
        //从TTable目录下获取
        try {
            File dictionary = new File(TTable.class.getResource("").toURI());
            for(File file : dictionary.listFiles()){
                String name = file.getName();
                if(name.endsWith(".class") && !name.equals("TTable.class")){
                    tableNames.add(name.substring(0,name.indexOf(".class")));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTableNames() {
        return tableNames;
    }

    //key:表名 value:TTable
    private static Map<String,TTable> tables = new HashMap<>();

    public static void putTTable(TTable tTable){
        tables.put(tTable.toString(),tTable);
    }

    public static Map<String, TTable> getTables() {
        return tables;
    }
}
