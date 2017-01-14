package com.thanple.thinking.berkeleyDB.myframework;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;

/**
 * Created by Thanple on 2017/1/13.
 */
public class BerkeleyDBManager {

    private static final String DB_DATA_PATH = "berkeley-db-data";
    private static final String DB_NAME = "berkeley-thanple";
    private static final boolean ALLOW_DUPLICATED_KEY = false;  //是否允许一个key对应多个value

    private Environment environment;
    private Database database;

    //Singleton
    private static BerkeleyDBManager berkeleyDBManager = new BerkeleyDBManager();
    private BerkeleyDBManager(){
    }
    public static BerkeleyDBManager getInstance(){
        return berkeleyDBManager;
    }

    //数据存放目录
    private File getEnvHomeFile() {
        File envHome = new File(DB_DATA_PATH);
        if (!envHome.exists()) {
            envHome.mkdirs();
        }
        return envHome;
    }

    public synchronized void open(){

        //EnvironmentConfig
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);

        //Environment
        environment = new Environment(this.getEnvHomeFile(),envConfig);

        //DatabaseConfig
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(true);
        // 设置一个key是否允许存储多个值
        dbConfig.setSortedDuplicates(ALLOW_DUPLICATED_KEY);

        //Open Database
        database = environment.openDatabase(null, DB_NAME, dbConfig);

    }



    public synchronized void close(){

        if(null != database){
            database.close();
        }

        if(null != environment){
            environment.close();
        }

    }

    public Environment getEnvironment() {
        if(null == database) throw new RuntimeException("Berkeley DB has not been inited!!!");
        return environment;
    }

    public Database getDatabase() {
        if(null == database) throw new RuntimeException("Berkeley DB has not been inited!!!");
        return database;
    }
}
