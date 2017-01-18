package com.thanple.thinking.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanple on 2017/1/18.
 */
public class Map_Test {

    public static void main(String[] args) {

        Map<Integer,String> map1 = new HashMap<>();
        map1.put(1,"test1");

        String s = map1.put(1,"test2");

        System.out.println(s);
    }
}
