package com.thanple.thinking.nio.ftp;

import java.nio.channels.SelectionKey;

/**
 * Created by Thanple on 2017/1/18.
 */
public interface NioHandler {
    void execute(SelectionKey key);
}
