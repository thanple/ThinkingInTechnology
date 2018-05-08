package com.thanple.thinking.springboot.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Thanple on 2018/5/8.
 */

@Data
public class Friend {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long asId;
}
