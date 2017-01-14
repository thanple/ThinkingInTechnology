package com.thanple.application.webspider.caishuo.Dao;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class CustomMysqlDialect  extends MySQL5InnoDBDialect {
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
