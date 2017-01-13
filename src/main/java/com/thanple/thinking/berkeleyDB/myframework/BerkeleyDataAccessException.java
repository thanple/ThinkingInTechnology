package com.thanple.thinking.berkeleyDB.myframework;

import com.thanple.thinking.berkeleyDB.demo1.BDBDataAccessException;

/**
 * Created by Thanple on 2017/1/13.
 */
public class BerkeleyDataAccessException extends RuntimeException {
    public BerkeleyDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BerkeleyDataAccessException(String message) {
        super(message);
    }

    public BerkeleyDataAccessException() {
    }

    public static BerkeleyDataAccessException throwMe(String message, Throwable cause) {
        throw new BerkeleyDataAccessException(message, cause);
    }
}
