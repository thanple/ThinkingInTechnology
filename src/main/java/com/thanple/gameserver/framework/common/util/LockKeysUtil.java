package com.thanple.gameserver.framework.common.util;



import com.thanple.gameserver.framework.common.berkeleydb.table.TTable;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thanple on 2017/1/17.
 */
public class LockKeysUtil {

    //锁的细粒度
    @Data
    public static class LockItem<T extends Serializable>{
        private TTable<T> ttTable;
        private Long key;
        private T entity;
        public LockItem(TTable ttTable,Long key,T entity){
            this.ttTable = ttTable;
            this.key = key;
            this.entity = entity;
        }
    }

    //锁住颗粒度
    private static LockKey<String> keyLock = new LockKey<String>();

    //备份锁住的 TTable+key
    private static ThreadLocal<List<LockItem> > localLockItems = new ThreadLocal<List<LockItem>>(){
        @Override
        protected List<LockItem> initialValue() {
            return new ArrayList<LockItem>();
        }
    };

    private static String getLockName(TTable table, Long key){
        return table.toString()+key;
    }

    /*
    * 先对锁进行排序，再加锁
    * 同一把锁的判断依据
    * tableName+key
    **/
    public static <T extends Serializable> void lock(TTable<T> ttTable, Long... keys) {
        List<Long> keyList = Arrays.asList(keys);
        Collections.sort(keyList);
        for(Long key : keyList){
            keyLock.lock(getLockName(ttTable,key));
        }
    }

    /*
    * 备份加锁的数据
    * */
    public static <T extends Serializable> void saveInThread(TTable<T> ttTable, Long key, T entity) {
        localLockItems.get().add(new LockItem(ttTable,key,entity));
    }

    public static List<LockItem> getLocalLockItems() {
        return localLockItems.get();
    }

    /*
    * 解锁
    * */
    public static <T extends Serializable> void unlock(TTable<T> ttTable, Long... keys) {
        for(Long key : keys){
            keyLock.unlock(getLockName(ttTable,key));
        }
    }

    //当前线程的所有锁释放
    public static void unlockAll(){
        keyLock.unlockInThread();
    }


}
