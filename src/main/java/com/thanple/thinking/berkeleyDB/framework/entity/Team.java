package com.thanple.thinking.berkeleyDB.framework.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/17.
 */
@Data
public class Team implements Serializable {
    private Long id;
    private Long userId;
    private String name;
}
