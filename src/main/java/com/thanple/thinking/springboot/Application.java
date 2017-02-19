package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.config.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Thanple on 2017/1/5.
 */
@SpringBootApplication
@ComponentScan//自动扫描bean
@EnableScheduling   //可以schedule
public class Application {

    @Autowired
    private MyConfig myConfig;

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}