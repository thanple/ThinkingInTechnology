package com.thanple.thinking.berkeleyDB.myframework;

import com.sleepycat.je.Transaction;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/13.
 */
public class BasicAccessObject <T extends Serializable> extends BerkeleyDBTemplate<Integer,T>{

    private Transaction transaction = BerkeleyTransaction.currentTransaction();

    public BasicAccessObject(){
        super(BerkeleyDBManager.getInstance().getDatabase());
    }

    /*
    * 插入不允许重复
    * */
    public boolean insert(Integer key,T value){
        if(null != this.select(key))   {
            BerkeleyDataAccessException.throwMe(String.format("insert %s key=%s failed: key has been exsited!",value.toString(), key));
        }
        return super.insert(key,value,transaction);
    }


    /*
    * 选择出来
    * */
    public T select(Integer key){
        return super.findOne(key,transaction);
    }

    /*
    * 加锁取出，当更新时数据库也跟着更新
    * */
    public T get(Integer key){

        return this.select(key);
    }



}
