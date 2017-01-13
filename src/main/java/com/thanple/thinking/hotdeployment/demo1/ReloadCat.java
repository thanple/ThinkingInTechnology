package com.thanple.thinking.hotdeployment.demo1;

import com.thanple.thinking.root.Constant;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Thanple on 2016/12/15.
 *
 * 热加载demo，加载/ext/demo1/Cat.class，修改Cat.java之后可以通过compile.cmd重新编译生成Cat.class，然后重新加载Cat.class，从而做到热加载的目的
 */
public class ReloadCat {

    private static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
    private static ClassLoader cl;
    private static Class catClass;

    private static final String CAT_PATH = Constant.Path.ROOT_PATH + "\\ext\\demo1\\";

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        reload();
        System.out.println("miao, reload, or exit");

        while(true){
            String cmd = br.readLine();
            if(cmd.equalsIgnoreCase("exit")){
                return;
            } else if(cmd.equalsIgnoreCase("reload")){
                reload();
                System.out.println("Cat reloaded.");
            } else if(cmd.equalsIgnoreCase("miao")){
                catClass.newInstance();
            }
        }
    }

    /**
     * 用自定义的类加载器重新加载(不能在ClassPath目录下，改目录下不会重新加载)
     * 使用exe/demo1/compile.cmd重新编译class
     * @throws Exception
     */
    public static synchronized void reload() throws Exception{
        URL[] externalURLs = new URL[]{new URL("file:"+CAT_PATH)};
        cl = new URLClassLoader(externalURLs);
        catClass = cl.loadClass("Cat");
        if(null == catClass)    throw new Exception("加载Cat类失败");
    }
}
