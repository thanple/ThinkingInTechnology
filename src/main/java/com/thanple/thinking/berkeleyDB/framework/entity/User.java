package com.thanple.thinking.berkeleyDB.framework.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/17.
 */
@Data
public class User implements Serializable {
    private Long id;
    private String name;
}
