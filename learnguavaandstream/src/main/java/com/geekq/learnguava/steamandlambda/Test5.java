package com.geekq.learnguava.steamandlambda;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/3
 *
 * Lambda表达式深入与流初步
 */
public class Test5 {

    public static void main(String[] args) {

        TheInteface i1 = ()->{};
        System.out.println(i1.getClass().getInterfaces()[0]);
        TheInteface i2 = ()->{};
        System.out.println(i2.getClass().getInterfaces()[0]);

        new Thread(()->{
            System.out.println("hello 3" +
                    "0world");
        }).start();
    }

    @FunctionalInterface
    interface TheInteface{
        void myMethod();
    }

    @FunctionalInterface
    interface TheInteface2{
        void myMethod2();
    }
}
