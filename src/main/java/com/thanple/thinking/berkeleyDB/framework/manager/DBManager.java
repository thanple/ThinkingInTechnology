package com.thanple.thinking.berkeleyDB.framework.manager;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.thanple.thinking.berkeleyDB.framework.dataprovider.ConfigConst;
import com.thanple.thinking.berkeleyDB.framework.dataprovider.TableLoader;
import com.thanple.thinking.berkeleyDB.framework.exception.BerkeleyTableNotFoundException;
import com.thanple.thinking.berkeleyDB.framework.table.TTable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Thanple on 2017/1/17.
 */
public class DBManager  {

        //环境
        private Environment environment;

        //是否open
        public boolean isOpen = false;

        //Singleton
        private static DBManager berkeleyDBManager = new DBManager();
        private DBManager(){
        }
        public static DBManager getInstance(){
            return berkeleyDBManager;
        }

        //数据存放目录
        private File getEnvHomeFile() {
            File envHome = new File(ConfigConst.DB_DATA_PATH);
            if (!envHome.exists()) {
                envHome.mkdirs();
            }
            return envHome;
        }

        public synchronized void open(){

            //EnvironmentConfig
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setTransactional(ConfigConst.TRANSACTION_ON_OFF);
            envConfig.setAllowCreate(ConfigConst.ALLOW_CREATE);

            //Environment
            environment = new Environment(this.getEnvHomeFile(),envConfig);

            //DatabaseConfig
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setTransactional(ConfigConst.TRANSACTION_ON_OFF);
            dbConfig.setAllowCreate(ConfigConst.ALLOW_CREATE);
            dbConfig.setSortedDuplicates(ConfigConst.ALLOW_DUPLICATED_KEY);     // 设置一个key是否允许存储多个值

            //Open Database
            for(String tableName :TableLoader.getTableNames()){
                Database table = environment.openDatabase(null, tableName, dbConfig);
                TTable tTable;
                try {
                    Class<TTable> tTableClass = (Class<TTable>) Class.forName(ConfigConst.TABLE_PACKAGE + "." +tableName);
                    tTable = tTableClass.getConstructor(Database.class).newInstance(table);
                } catch (ClassNotFoundException e) {
                    throw new BerkeleyTableNotFoundException(String.format("Table tableName of Class is not found:"),e);
                } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
                    throw new BerkeleyTableNotFoundException(String.format("Table tableName of Contruct is error:"),e);
                }

                TableLoader.putTTable(tTable);
            }

            isOpen = true;

        }

        public synchronized void close(){

            //关闭每个数据库
            Map<Class, TTable> tables= TableLoader.getTables();
            if(null != tables){
                for(TTable table : tables.values()){
                    table.close();
                }
            }

            //关闭环境
            if(null != environment){
                environment.close();
            }

            isOpen = false;
        }

        public Environment getEnvironment() {
            return environment;
        }
}

