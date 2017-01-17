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

}
