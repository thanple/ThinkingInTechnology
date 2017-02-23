package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.config.MyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Thanple on 2017/1/5.
 *
 * 重新编译代码 ctrl+shift+F9
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MyConfig myConfig;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("MyConfig Value{},{}",myConfig.getValue1(),myConfig.getValue2());
    }
}