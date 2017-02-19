package com.thanple.thinking.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thanple on 2017/2/19.
 * 当集合碰上通配泛型
 */

class A{}
class B extends A{}
class C<E>{
    public void add(E e){}
}
class D<E extends A>{
    public void add(E e){}
}

public class List_T_Test {

    public static void main(String[] args) {
        List<? extends A> list = new ArrayList<>();
//        list.add(new B());    //编译不通过

        C<? extends A> c = new C();
//        c.add(new B());         //编译不通过

        C<A> cc = new C<>();
        testCC(cc);

        D<B> d = new D();
        d.add(new B());

        List<? extends Map> mapList = new ArrayList<>();
//        mapList.add(new HashMap()); //编译不通过

        A[] as = new B[1];
        as[0] = new B();

    }

    public static void  testCC(C<? extends A> e){}
}
