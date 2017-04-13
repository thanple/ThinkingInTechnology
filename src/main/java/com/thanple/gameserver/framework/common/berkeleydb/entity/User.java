package com.thanple.gameserver.framework.common.berkeleydb.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/17.
 */
@Data
public class User implements Serializable {
    private long id;
    private String name = "";
}
