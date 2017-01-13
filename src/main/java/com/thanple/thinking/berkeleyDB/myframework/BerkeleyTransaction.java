package com.thanple.thinking.berkeleyDB.myframework;

import com.sleepycat.je.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanple on 2017/1/13.
 */
public class BerkeleyTransaction {
    private static ThreadLocal<Transaction> current = new ThreadLocal<>();
    private static ThreadLocal<ArrayList<Boolean>> results = new ThreadLocal<ArrayList<Boolean>>(){
        protected ArrayList initialValue() {
            return new ArrayList();
        }
    };

    public static void clearResults(){
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

    public static boolean  commit(){
        boolean rs = false;
        for(Boolean e : results.get()){
            if(!e){
                current.get().abort();
                rs  = false;
                break;
            }
        }
        current.get().commit();
        results.get().clear();  //清空事务记录
        return rs;
    }

}
