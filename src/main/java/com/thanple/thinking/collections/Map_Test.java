package com.thanple.thinking.collections;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thanple on 2017/1/18.
 */
public class Map_Test {

    @Data
    private static class TestObject {
        private String name;

        @Override
        public int hashCode() {
            return 5;
        }
    }

    public static void main(String[] args) {

        Map<Integer,String> map1 = new HashMap<>();
        map1.put(1,"test1");

        String s = map1.put(1,"test2");

        System.out.println(s);

        Map<TestObject,String> map2 = new HashMap<>();
        TestObject testObject1 = new TestObject();
        testObject1.setName("Thanple");
        testObject1.setName("Thanple2");
        TestObject testObject2 = new TestObject();
        testObject2.setName("Tom");
        map2.put(testObject1,"1");
        map2.put(testObject2,"2");

        System.out.println(map2.get(testObject1));
        System.out.println(map2.get(testObject2));
    }
}
