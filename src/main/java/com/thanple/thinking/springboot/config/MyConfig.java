package com.thanple.thinking.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Thanple on 2017/1/4.
 *
 * 读取配置文件
 */

@Component
@Data
public class MyConfig {

    @Bean("MyConfig")
    public MyConfig myConfig(){
        return this;
    }

    @Value("${MyConfig.value1}")
    private String value1;

    @Value("${MyConfig.value2}")
    private String value2;

    @Value("${MyConfig.API_ON_OFF}")
    private Boolean API_ON_OFF;

    @Value("${MyConfig.mybatisXmlPackage}")
    private String mybatisXmlPackage;

}
