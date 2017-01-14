package com.thanple.application.webspider.caishuo.Model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
@Data
public class BasketInf  implements Serializable {
    private Long id;
    private Long basketId;
    private String industry;    //行业
    private String rate;    //比重
    private String code;    //代码
    private String company; //公司名称
    private String reason;  //入选理由
}
