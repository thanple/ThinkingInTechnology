package com.thanple.gameserver.framework.common.berkeleydb;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thanple on 2017/1/17.
 */
public class TransactionManager {

    //加锁最长时间
    private static final int LOCK_TIME_OUT = 10000;

    private static ThreadLocal<Transaction> current = new ThreadLocal<>();
    private static ThreadLocal<ArrayList<Boolean>> results = new ThreadLocal<ArrayList<Boolean>>(){
        protected ArrayList initialValue() {
            return new ArrayList();
        }
    };

    public static void clearResults(){
        current.remove();
        results.get().clear();
    }

    public static Transaction currentTransaction(){
        return current.get();
    }

    public static void setTransaction(Transaction transaction){
        current.set(transaction);
    }

    public static void savePoint(Boolean point){
        results.get().add(point);
    }

    //开启事务
    public static void startTransaction(){
        Environment environment = DBManager.getInstance().getEnvironment();
        if(null == currentTransaction()){
            Transaction transaction = environment.beginTransaction(null, TransactionConfig.DEFAULT);
            transaction.setLockTimeout(LOCK_TIME_OUT, TimeUnit.MILLISECONDS);
            TransactionManager.setTransaction(transaction);
        }
    }

    //提交或者回滚事务
    public static boolean commit(){
        boolean rs = true;
        for(Boolean e : results.get()){
            if(!e){
                current.get().abort();
                rs  = false;
                break;
            }
        }
        if(rs)  current.get().commit();
        clearResults();  //清空事务记录
        return rs;
    }
}
