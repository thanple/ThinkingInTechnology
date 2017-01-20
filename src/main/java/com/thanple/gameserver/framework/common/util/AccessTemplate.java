package com.thanple.gameserver.framework.common.util;

import com.sleepycat.je.*;
import com.thanple.gameserver.framework.common.exception.BerkeleyDataAccessException;
import com.thanple.gameserver.framework.common.provider.ConfigConst;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/17.
 */
public class AccessTemplate <K extends Serializable, V extends Serializable>{

    protected Database database;

    public AccessTemplate(Database database){
        this.database = database;
    }

    public boolean insert(K key,V value,Transaction transaction){
        return this.insert(key,value,transaction, !ConfigConst.ALLOW_DUPLICATED_KEY);
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
            DatabaseEntry keyEntry = new DatabaseEntry(com.thanple.thinking.util.SerializeUtil.serialize(key));
            DatabaseEntry valEntry = new DatabaseEntry(com.thanple.thinking.util.SerializeUtil.serialize(value));

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
            DatabaseEntry keyEntry = new DatabaseEntry(com.thanple.thinking.util.SerializeUtil.serialize(key));
            DatabaseEntry valueEntry = new DatabaseEntry();

            OperationStatus status = database.get(transaction, keyEntry, valueEntry, LockMode.DEFAULT);

            if(status==OperationStatus.SUCCESS){
                result = valueEntry.getData();
            }
        } catch (Exception e) {
            BerkeleyDataAccessException.throwMe(String.format("find key=%s failed", key), e);
        }

        return com.thanple.thinking.util.SerializeUtil.unserialize(result);
    }




}

