package com.thanple.gameserver.framework.common.berkeleydb.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanple on 2017/1/17.
 */
public class Team implements Serializable {
    private long id;
    private List<Long> roles = new ArrayList<>();
    private String name = "";
}
