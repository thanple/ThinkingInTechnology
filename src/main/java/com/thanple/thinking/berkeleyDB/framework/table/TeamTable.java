package com.thanple.thinking.berkeleyDB.framework.table;

import com.sleepycat.je.Database;
import com.thanple.thinking.berkeleyDB.framework.dataprovider.TableLoader;
import com.thanple.thinking.berkeleyDB.framework.entity.Team;

/**
 * Created by Thanple on 2017/1/17.
 */
public class TeamTable extends TTable<Team>{
    public TeamTable(Database table) {
        super(table);
    }

}
