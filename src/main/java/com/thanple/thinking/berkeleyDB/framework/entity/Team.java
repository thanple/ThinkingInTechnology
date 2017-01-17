package com.thanple.thinking.berkeleyDB.framework.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanple on 2017/1/17.
 */
@Data
public class Team extends Entity {
    private List<Long> roles = new ArrayList<>();
    private String name = "";
}
