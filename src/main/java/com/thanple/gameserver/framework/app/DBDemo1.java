package com.thanple.gameserver.framework.app;

import com.thanple.gameserver.framework.common.berkeleydb.DBManager;
import com.thanple.gameserver.framework.common.berkeleydb.Procedure;
import com.thanple.gameserver.framework.common.berkeleydb.entity.User;
import com.thanple.gameserver.framework.common.berkeleydb.table.UserTable;
import com.thanple.gameserver.framework.common.provider.TableLoader;

import java.util.concurrent.ExecutionException;

/**
 * Created by Thanple on 2017/1/20.
 */
public class DBDemo1 {

    private static DBManager manager = DBManager.getInstance();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        manager.open();

        new Procedure() {
            @Override
            protected boolean process() {

                User user = new User();
                user.setId(1L);
                user.setName("Thanple1");
                TableLoader.getTableInstance(UserTable.class).save(user.getId(),user);

                pexecute(new Procedure() {
                    @Override
                    protected boolean process() {
                        User user = new User();
                        user.setId(2L);
                        user.setName("Thanple2");

                        TableLoader.getTableInstance(UserTable.class).save(user.getId(),user);

                        return true;
                    }
                });

                return true;
            }
        }.call();

        new Procedure() {
            @Override
            protected boolean process() {

                UserTable user = TableLoader.getTableInstance(UserTable.class);
                User userEntity1 = user.select(1L);
                User userEntity2 = user.select(2L);

                System.out.println(userEntity1);
                System.out.println(userEntity2);

                return true;
            }
        }.call();


        Thread.sleep(2000);
        manager.close();
        System.exit(0);
    }
}
