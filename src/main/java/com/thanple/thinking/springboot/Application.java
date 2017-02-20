package com.thanple.thinking.springboot;

import com.thanple.thinking.springboot.config.MyConfig;
import com.thanple.thinking.springboot.jdbc.JTemplateDemo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Thanple on 2017/1/5.
 */
@SpringBootApplication
@Configuration
@ComponentScan//自动扫描bean
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})   //启用自动配置
@EnableScheduling   //可以schedule
public class Application implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(Application.class);

    @Autowired
    private MyConfig myConfig;

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Autowired
    private JTemplateDemo jTemplateDemo;
    @Override
    public void run(String... strings) throws Exception {
//        jTemplateDemo.run(strings);
    }
}