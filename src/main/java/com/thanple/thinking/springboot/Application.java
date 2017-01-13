package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.config.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Thanple on 2017/1/5.
 */
@SpringBootApplication
public class Application {

    @Autowired
    private MyConfig myConfig;

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}