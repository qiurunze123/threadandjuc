package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 *
 * 饿汉式 静态代码块 -- 可用
 */
public class Singleton2 {

    private final static  Singleton2 INSTANCE ;

    static{
        INSTANCE = new Singleton2();
    }
    public Singleton2(){

    }

    public static Singleton2 getInstance(){
        return INSTANCE ;
    }
}
