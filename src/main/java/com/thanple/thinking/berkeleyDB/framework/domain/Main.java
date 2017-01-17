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
 * Created by Thanple on 2017/1/17.
 */
public class Main {

    private static DBManager manager = DBManager.getInstance();

    public static void main(String[] args) {
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
        }.submit();

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
        }.submit();




        //get测试
        new Procedure() {
            @Override
            protected boolean process() {

                UserTable user = TableLoader.getTableInstance(UserTable.class);
                User userEntity1 = user.get(1L);
                User userEntity2 = user.get(2L);

                userEntity1.setName("Tom1");
                userEntity2.setName("Tom2");

                throw new RuntimeException("测试锁的释放");

//                return true;
            }
        }.submit();
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
        }.submit();



        //死锁测试
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future> list = new ArrayList<>();
        for(int i=0;i<2;i++){
            final int count = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    LockKeysUtil.lock(TableLoader.getTableInstance(UserTable.class),1L,2L);  //锁排序
                    if(count%2==0){
                        testDeadLock(1L,2L);
                    }else{
                        testDeadLock(2L,1L);
                    }

                }
            };
            list.add(executorService.submit(runnable));
        }

        for(Future future : list){
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdownNow();
        manager.close();
    }



    public static  void testDeadLock(Long user1,Long user2){
        new Procedure() {
            @Override
            protected boolean process() {

                UserTable user = TableLoader.getTableInstance(UserTable.class);
                User userEntity1 = user.get(user1);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                User userEntity2 = user.get(user2);

                System.out.println("entity1:"+userEntity1);
                System.out.println("entity2:"+userEntity2);

                return true;
            }
        }.submit();
    }
}
