package com.thanple.thinking.springboot.entity;

import lombok.Data;

/**
 * Created by Thanple on 2017/2/19.
 */

@Data
public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}