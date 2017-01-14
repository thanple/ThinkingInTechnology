package com.thanple.application.webspider.caishuo.Model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
@Data
public class Basket implements Serializable{
    private Long id;
    private String href;
    private String title;

    private String author;  //作者
    private String time;    //时间
    private Integer attentions; //关注数
    private String tags;    //组合标签
}
