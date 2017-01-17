package com.thanple.thinking.berkeleyDB.framework.table;

import com.sleepycat.je.Database;
import com.thanple.thinking.berkeleyDB.framework.dataprovider.TableLoader;
import com.thanple.thinking.berkeleyDB.framework.entity.User;


/**
 * Created by Thanple on 2017/1/17.
 */
public class UserTable extends TTable<User>{
    public UserTable(Database table) {
        super(table);
    }

    public static UserTable getInstance(){
        return (UserTable)TableLoader.getTables().get("UserTable");
    }


}
