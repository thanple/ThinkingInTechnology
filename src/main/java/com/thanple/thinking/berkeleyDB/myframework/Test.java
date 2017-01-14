package com.thanple.thinking.berkeleyDB.myframework;

import java.io.Serializable;

/**
 * Created by Thanple on 2017/1/13.
 */
public class Test {



    private static class Obj1 implements Serializable{
        private int a;
        private int b;
        private String c;

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public String getC() {
            return c;
        }

        public void setA(int a) {
            this.a = a;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setC(String c) {
            this.c = c;
        }

    }

    private static class Dao extends BasicAccessObject<Obj1>{

    }

    private static class Procedure11 extends Procedure{

        @Override
        protected boolean process() {

            Dao dao = new Dao();

            Obj1 obj1 = new Obj1();
            obj1.setA(1);
            obj1.setB(2);
            obj1.setC("11");

            dao.insert(1,obj1);

            //事务传播测试
            pexecute(new Procedure12());

            return true;
        }
    }
    private static class Procedure12 extends Procedure{

        @Override
        protected boolean process() {

            Dao dao = new Dao();

            Obj1 obj1 = new Obj1();
            obj1.setA(1);
            obj1.setB(2);
            obj1.setC("12");
            dao.insert(2,obj1);

            return true;
        }
    }

    private static class Procedure2 extends Procedure{

        @Override
        protected boolean process() {

            Dao dao = new Dao();
            Obj1 obj1 = dao.select(1);
            System.out.println("a="+obj1.getA()+" b="+obj1.getB()+" c="+obj1.getC());

            obj1 = dao.select(2);
            System.out.println("a="+obj1.getA()+" b="+obj1.getB()+" c="+obj1.getC());

            return true;
        }
    }

    public static void main(String[] args) {

        BerkeleyDBManager.getInstance().open();

        new Procedure11().submit(); //插入数据
        new Procedure2().submit(); //读取数据

//        BerkeleyDBManager.getInstance().close();
    }
}
