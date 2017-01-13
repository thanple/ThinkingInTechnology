package com.thanple.thinking.berkeleyDB.demo1;


public class BDBDataAccessException extends RuntimeException {

    public BDBDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BDBDataAccessException(String message) {
        super(message);
    }

    public BDBDataAccessException() {
    }

    public static BDBDataAccessException throwMe(String message, Throwable cause) {
        throw new BDBDataAccessException(message, cause);
    }
}
