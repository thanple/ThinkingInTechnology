package com.thanple.thinking.springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Thanple on 2017/1/5.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}