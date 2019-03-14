package com.thanple.thinking.jvm;

/**
 * HotSwapAgent: 可以在生产环境热加载方法体代码
 * http://hao.jobbole.com/hotswapagent%EF%BC%9A%E6%94%AF%E6%8C%81%E6%97%A0%E9%99%90%E6%AC%A1%E9%87%8D%E5%AE%9A%E4%B9%89%E8%BF%90%E8%A1%8C%E6%97%B6%E7%B1%BB%E4%B8%8E%E8%B5%84%E6%BA%90-2/?utm_source=www.jobbole.com&utm_medium=homepage-resources
 *
 * Create by Thanple at 2018/8/2 上午8:09
 * -XXaltjvm=dcevm -javaagent:.lib/hotswap-agent-1.3.0.jar=autoHotswap=true
 */

class Person  {
    private static int i = 0;
    public String hello(){
        return "hello"+i++;
    }
    public String toString(){
        Person2 pe = new Person2();
        return "from person1,"+hello()+" : "+pe.toString();
    }
}
class Person2  {
    public String hello(){
        return "hello";
    }
    public String toString(){
        return "from person2,"+hello();
    }
}
class Person3  {
    public String hello(){
        return "hello";
    }
    public String toString(){
        return "from person3,"+hello();
    }
}


public class HotSwapAgentTest {

    public static void main(String[] args) {
        final Person p1 = new Person();  //内存只有一个实例对象
        while(true){
            try{
                Thread.sleep(1000);
                System.err.println(p1);
            }catch(Exception e){

            }
        }
    }
}
