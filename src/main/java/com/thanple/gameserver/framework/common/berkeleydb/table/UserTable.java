package com.thanple.gameserver.framework.common.berkeleydb.table;

import com.sleepycat.je.Database;
import com.thanple.gameserver.framework.common.berkeleydb.entity.User;


/**
 * Created by Thanple on 2017/1/17.
 */
public class UserTable extends TTable<User> {
    public UserTable(Database table) {
        super(table);
    }


}
