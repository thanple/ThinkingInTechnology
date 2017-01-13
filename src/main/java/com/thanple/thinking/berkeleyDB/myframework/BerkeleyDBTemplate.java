package com.thanple.thinking.berkeleyDB.myframework;

import com.sleepycat.je.*;
import com.thanple.thinking.berkeleyDB.demo1.BDBDataAccessException;
import com.thanple.thinking.util.SerializeUtil;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/13.
 */
public class BerkeleyDBTemplate <K extends Serializable , V extends Serializable>{

    private Database database;

    public BerkeleyDBTemplate(Database database){
        this.database = database;
    }

    public boolean insert(K key,V value,Transaction transaction){
        return this.insert(key,value,transaction,false);
    }

    /**
     * 插入一条记录
     * @param K key
     * @param V value
     * @param Transaction transaction
     * @param boolean isOverwrite
     * @return boolean
     * */
    public boolean insert(K key, V value, Transaction transaction ,boolean isOverwrite){
        OperationStatus status = null;
        try {
            DatabaseEntry keyEntry = new DatabaseEntry(SerializeUtil.serialize(key));
            DatabaseEntry valEntry = new DatabaseEntry(SerializeUtil.serialize(value));

            if (isOverwrite) {
                status = database.put(transaction, keyEntry, valEntry);
            } else {
                status = database.putNoOverwrite(transaction, keyEntry, valEntry);
            }
        } catch (Exception e) {
            BerkeleyDataAccessException.throwMe(String.format("insert key=%s , value = %s failed", key, value), e);
        }
        return null !=status && status == OperationStatus.SUCCESS;
    }

    /**
     * 查询一条记录
     * @param K key
     * @param Transaction transaction
     * @return V
     * */
    public V findOne(K key,Transaction transaction){
        byte [] result = null;
        try {
            DatabaseEntry keyEntry = new DatabaseEntry(SerializeUtil.serialize(key));
            DatabaseEntry valueEntry = new DatabaseEntry();

            OperationStatus status = database.get(transaction, keyEntry, valueEntry, LockMode.DEFAULT);

            if(status==OperationStatus.SUCCESS){
                result = valueEntry.getData();
            }
        } catch (Exception e) {
            BerkeleyDataAccessException.throwMe(String.format("find key=%s failed", key), e);
        }

        return SerializeUtil.unserialize(result);
    }


}
