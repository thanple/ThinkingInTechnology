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

    public boolean insert(Integer key,T value){
        return super.insert(key,value,transaction);
    }


    public T select(Integer key){
        return super.findOne(key,transaction);
    }



}
