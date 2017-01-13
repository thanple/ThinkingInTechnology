package com.thanple.thinking.berkeleyDB.myframework;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.TransactionConfig;

/**
 * Created by Thanple on 2017/1/13.
 */
public abstract class Procedure {

    //回调处理函数
    protected abstract boolean process();

    public void submit(){

        //开启事务
        Environment environment = BerkeleyDBManager.getInstance().getEnvironment();
        if(null == BerkeleyTransaction.currentTransaction()){
            Transaction transaction = environment.beginTransaction(null, TransactionConfig.DEFAULT);
            BerkeleyTransaction.setTransaction(transaction);
        }

        //处理逻辑
        boolean result = this.process();
        BerkeleyTransaction.savePoint(result);

        //返回结果

    }

}
