package com.thanple.gameserver.framework.common.berkeleydb;


import com.thanple.gameserver.framework.common.berkeleydb.table.TTable;
import com.thanple.gameserver.framework.common.exception.BerkeleyNotInProcedureException;
import com.thanple.gameserver.framework.common.util.ExecutorUtil;
import com.thanple.gameserver.framework.common.util.LockKeysUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Thanple on 2017/1/13.
 *
 * 每个Procedure都是一个线程，包括同步和异步，线程之间遵循可串行化事务隔离度
 */
public abstract class Procedure {

    /*
    * 责任链模式
    * 为什么要这样设计呢，
    * 设置一个Procedure集合的目的是一个Procedure里面可以嵌套多个Procedure，
    * 相当于是一个队列，按照顺序轮训调度
    * */
    private ThreadLocal<ArrayList<Procedure>> procedureList = new ThreadLocal<ArrayList<Procedure>>(){
        protected ArrayList<Procedure> initialValue() {
            return new ArrayList<Procedure>();
        }
    };
    private ThreadLocal<ArrayList<Procedure>> procedureAfterCommitList = new ThreadLocal<ArrayList<Procedure>>(){
        protected ArrayList<Procedure> initialValue() {
            return new ArrayList<Procedure>();
        }
    };
    private ThreadLocal<ArrayList<Procedure>> procedureAfterRollBackList = new ThreadLocal<ArrayList<Procedure>>(){
        protected ArrayList<Procedure> initialValue() {
            return new ArrayList<Procedure>();
        }
    };

    //回调处理函数
    protected abstract boolean process();


    private boolean runProcedure(){
        try {

            //进入Procedure标志
            BerkeleyNotInProcedureException.beginInTheProcedure();

            //开启事务
            TransactionManager.startTransaction();

            this.process();

            //更新锁住的数据（即get出来的数据）
            for(LockKeysUtil.LockItem lockItem : LockKeysUtil.getLocalLockItems()){
                TTable tTable = lockItem.getTtTable();
                Long key = lockItem.getKey();
                Serializable entityValue = lockItem.getEntity();

                tTable.save(key,entityValue);
            }

        }catch (Exception e){
            TransactionManager.savePoint(false);    //回滚事务标志
            e.printStackTrace();
        }finally {

            //get出来的数据保存的引用全部清空
            LockKeysUtil.getLocalLockItems().clear();

            //释放keylock(这样做的好处是当前Procedure如果抛出异常也继续释放锁)
            LockKeysUtil.unlockAll();

            //提交事务(返回false时已经提交)
            boolean transactionSucess = TransactionManager.commit();
            if(transactionSucess){
                procedureAfterCommitList.get().forEach(p->{
                    p.submit();
                });
            }else{
                procedureAfterRollBackList.get().forEach(p->{
                    p.submit();
                });
            }
            procedureList.get().forEach(p->{
                p.submit();
            });

            //离开Procedure标志
            BerkeleyNotInProcedureException.endInTheProcedure();

            return transactionSucess;
        }
    }


    public Future<Boolean> submit(){
        Future<Boolean> future = ExecutorUtil.executeFuture(()-> Procedure.this.runProcedure());
        return future;
    }

    /**
     * 同步执行函数Call，等待Procedure执行完之后执行
     * */
    public boolean call(Procedure procedure) throws ExecutionException, InterruptedException {
        return procedure.call();
    }
    public boolean call() throws ExecutionException, InterruptedException {
        return this.submit().get();
    }

    //异步执行函数,当父Procedure执行完后执行
    public void pexecute(Procedure procedure){
        procedureList.get().add(procedure);
    }

    //异步执行函数，当父Procedure提交后执行
    public void pexecuteWhileCommit(Procedure procedure){
        procedureAfterCommitList.get().add(procedure);
    }

    //异步执行函数，当父Procedure回滚后执行
    public void pexecuteWhileRollBack(Procedure procedure){
        procedureAfterRollBackList.get().add(procedure);
    }



}
