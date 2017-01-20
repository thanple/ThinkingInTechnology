package com.thanple.gameserver.framework.common.berkeleydb.table;

import com.sleepycat.je.Database;
import com.thanple.gameserver.framework.common.berkeleydb.entity.Team;

/**
 * Created by Thanple on 2017/1/17.
 */
public class TeamTable extends TTable<Team> {
    public TeamTable(Database table) {
        super(table);
    }

}
