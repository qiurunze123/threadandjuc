package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 *
 * 饿汉式 静态常量可用
 */
public class Singleton1 {

    private final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1(){

    }

    public static  Singleton1 getInstance(){
        return INSTANCE;
    }
}
