package com.thanple.gameserver.framework.common.exception;

/**
 * Created by Thanple on 2017/1/17.
 */
public class BerkeleyTableNotFoundException extends RuntimeException {
    public BerkeleyTableNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
    public BerkeleyTableNotFoundException(String message){
        super(message);
    }
}
