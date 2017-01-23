package com.thanple.gameserver.framework.common.util;

import com.thanple.thinking.root.Constant;

/**
 * Created by Thanple on 2017/1/23.
 */
public class CommonUtil {

    /**
     * 将包名转换成文件夹工具
     * */
    public static String getDitionaryByPackage(String packagez){
        return Constant.Path.SRC_ROOT_PATH + "/" + packagez.replace(".","/");
    }

    /**
     * 获取包名
     * */
    public static String getPackageByClassName(String classz){
        return classz.substring(0,classz.lastIndexOf("."));
    }

    /**
     * 获取类名
     * */
    public static String getSimpleNameByClassName(String classz){
        return classz.substring(classz.lastIndexOf(".")+1 ,classz.length());
    }



}
