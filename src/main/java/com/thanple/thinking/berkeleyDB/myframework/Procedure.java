package com.thanple.thinking.berkeleyDB.myframework;


import java.util.ArrayList;

/**
 * Created by Thanple on 2017/1/13.
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

    //回调处理函数
    protected abstract boolean process();


    /*
    * 运行Procedure，但是不提交事务，运行完后所有结果保存在BerkeleyTransaction中
    * */
    private void runProcedure(){

        //保存处理结果
        boolean result = this.process();
        BerkeleyTransaction.savePoint(result);

        //子Procedure处理结果
        for (Procedure p : procedureList.get()){
            p.runProcedure();
        }
    }

    public void submit(){

        //开启事务
        BerkeleyTransaction.startTransaction();

        //执行procedure
        this.runProcedure();

        //更新锁住的数据（即get出来的数据）


        //释放keylock
        BerkeleyKeyLockUtil.unlockAll();

        //提交事务
        BerkeleyTransaction.commit();
    }

    //同步执行函数,当形参执行完后执行
    //放到队列里面，等commit的时候统一执行
    public void pexecute(Procedure procedure){
        procedureList.get().add(procedure);
    }

}
