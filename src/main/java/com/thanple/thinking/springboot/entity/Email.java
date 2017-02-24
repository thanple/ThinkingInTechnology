package com.thanple.thinking.springboot.entity;

import lombok.Data;

/**
 * Created by Thanple on 2017/2/24.
 */
@Data
public class Email {
    private String to;
    private String body;

    public Email() {
    }

    public Email(String to, String body) {
        this.to = to;
        this.body = body;
    }
}
