package com.thanple.hadoop.demo;

/**
 * Create by Thanple at 2017/12/18 下午10:16
 *
 * 从文件导入到mysql
 *
 CREATE TABLE test_01 (a int, b int, c int) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' STORED AS TEXTFILE;
 LOAD DATA LOCAL INPATH 'file:///Users/thanple/IdeaProjects/ThinkingInTechnology/src/main/resources/com/thanple/hadoop/hivedemo/input1.txt' OVERWRITE INTO TABLE test_01 ;


 * 与hive交互的方式：
 * 1.CLI命令行
 * 2.WebUI交互
 * 3.使用JDBC
 */
public class HiveDemo {
}
