package com.thanple.thinking.berkeleyDB.framework.domain;

import com.thanple.thinking.berkeleyDB.framework.dataprovider.TableLoader;
import com.thanple.thinking.berkeleyDB.framework.entity.User;
import com.thanple.thinking.berkeleyDB.framework.manager.DBManager;
import com.thanple.thinking.berkeleyDB.framework.table.UserTable;
import com.thanple.thinking.berkeleyDB.framework.util.LockKeysUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Thanple on 2017/1/18.
 */
public class Main2 {

    private static DBManager manager = DBManager.getInstance();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        manager.open();

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.testDeadLock(1L,2L);
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //死锁测试
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future> futureList = new ArrayList<>();
        for(int i=0;i<100;i++){
            Future future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Main.testDeadLock(1L,2L);
                }
            });
            futureList.add(future);
        }



        for(Future future : futureList){
            future.get();
        }
        executorService.shutdownNow();

        manager.close();
    }
}
