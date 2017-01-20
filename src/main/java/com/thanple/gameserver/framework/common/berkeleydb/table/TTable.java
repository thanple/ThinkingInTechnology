package com.thanple.gameserver.framework.common.berkeleydb.table;

import com.sleepycat.je.Database;
import com.thanple.gameserver.framework.common.berkeleydb.TransactionManager;
import com.thanple.gameserver.framework.common.exception.BerkeleyDataAccessException;
import com.thanple.gameserver.framework.common.exception.BerkeleyNotInProcedureException;
import com.thanple.gameserver.framework.common.util.AccessTemplate;
import com.thanple.gameserver.framework.common.util.LockKeysUtil;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/17.
 */
public abstract class TTable<T extends Serializable> extends AccessTemplate<Long,T> {

    public TTable(Database table) {
        super(table);
    }

    /*
   * 插入不允许重复
   * */
    public boolean insert(Long key,T value){
        BerkeleyNotInProcedureException.checkInProcedure();
        if(null != this.select(key))   {
            BerkeleyDataAccessException.throwMe(String.format("insert %s key=%s failed: key has been exsited!",value.toString(), key));
        }
        return super.insert(key,value, TransactionManager.currentTransaction());
    }

    /*
    * 选择出来
    * */
    public T select(Long key){
        BerkeleyNotInProcedureException.checkInProcedure();
        return super.findOne(key,TransactionManager.currentTransaction());
    }

    /*
    * 加锁取出，当更新时数据库也跟着更新
    * */
    public T get(Long key){
        BerkeleyNotInProcedureException.checkInProcedure();
        LockKeysUtil.lock(this,key);    //先加锁，再选出来
        T entityValue = this.select(key);
        LockKeysUtil.saveInThread(this,key,entityValue);    //保存需要更新的数据的一个引用
        return entityValue;
    }


    /**
     *  更新数据
     * */
    public void save(Long key,T value){
        BerkeleyNotInProcedureException.checkInProcedure();
        super.insert(key,value, TransactionManager.currentTransaction());
    }

    //关闭数据库(表)
    public void close(){
        database.close();
    }

    //根据类名创建数据库（表）
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
