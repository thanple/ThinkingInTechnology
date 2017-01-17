package com.thanple.thinking.berkeleyDB.demoframework;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Thanple on 2017/1/15.
 * 锁的工具类
 */
public class BerkeleyKeyLockUtil {

    private static BerkeleyKeyLock<String> keyLock = new BerkeleyKeyLock<String>();


    private static String getLockName(TTable table,Integer key){
        return table.toString()+key;
    }

    /*
    * 同一把锁的判断依据
    * tableName+key
    * */
    public static void lock(TTable table,Integer key){
        keyLock.lock(getLockName(table,key));
    }

    /*
    * 先对锁进行排序，再加锁
    * */
    public static void lock(TTable table,Integer... keys){
        List<Integer> keyList = Arrays.asList(keys);
        Collections.sort(keyList);
        for(Integer key : keyList){
            lock(table,key);
        }
    }

    public static void unlock(TTable table,Integer key){
        keyLock.unlock(getLockName(table,key));
    }
    public static void unlock(TTable table,Integer... keys){
        for(Integer key : keys){
            unlock(table,key);
        }
    }

    //当前线程的所有锁释放
    public static void unlockAll(){
        keyLock.unlockInThread();
    }


}
