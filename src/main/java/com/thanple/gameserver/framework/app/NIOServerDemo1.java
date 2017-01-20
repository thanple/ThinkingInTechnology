package com.thanple.gameserver.framework.app;

import com.thanple.gameserver.framework.common.berkeleydb.DBManager;
import com.thanple.gameserver.framework.common.nio.NettyServerManager;

/**
 * Created by Thanple on 2017/1/20.
 */
public class NIOServerDemo1 {

    private static NettyServerManager serverManager = NettyServerManager.getInstance();
    private static DBManager dbManager = DBManager.getInstance();

    public static void main(String[] args) {
        dbManager.open();

        serverManager.init();


        dbManager.close();
    }
}
