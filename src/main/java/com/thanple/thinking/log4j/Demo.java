package com.thanple.thinking.log4j;

import org.apache.log4j.Logger;

/**
 * Created by Thanple on 2017/1/23.
 * log4j的配置及其使用方法
 *
 * 注意：springboot会默认会使得log4j不能用，因为其默认使用logback日志记录
 *
 * 在pom.xml中修改如下
 * <!-- Springboot -->
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-web</artifactId>
 * <exclusions>
 * <exclusion>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-logging</artifactId>
 * </exclusion>
 * </exclusions>
 * </dependency>
 * <!-- spring boot默认使用logback日志记录工具，修改为log4j -->
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-log4j</artifactId>
 * </dependency>
 *
 */
public class Demo {

    private static Logger logger = Logger.getLogger(Demo.class);

    public static void main(String[] args) {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
}
