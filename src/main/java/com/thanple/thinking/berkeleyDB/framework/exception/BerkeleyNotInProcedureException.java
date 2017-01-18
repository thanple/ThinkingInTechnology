package com.thanple.thinking.berkeleyDB.framework.exception;

/**
 * Created by Thanple on 2017/1/17.
 */
public class BerkeleyNotInProcedureException extends RuntimeException{
    public BerkeleyNotInProcedureException(String message,Throwable throwable){
        super(message,throwable);
    }
    public BerkeleyNotInProcedureException(String message){
        super(message);
    }
    public BerkeleyNotInProcedureException(){
    }

    public static ThreadLocal<Boolean> inTheProcedure = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static void beginInTheProcedure(){
        inTheProcedure.set(Boolean.TRUE);
    }
    public static void endInTheProcedure(){
        inTheProcedure.set(Boolean.FALSE);
    }

    /**
     *
     * */
    public static void checkInProcedure(){
        if(!inTheProcedure.get())   throw new BerkeleyNotInProcedureException("Current thread is not in procedure causing errors!!!");
    }

}
