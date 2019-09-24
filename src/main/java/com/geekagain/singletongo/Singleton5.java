package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 *
 * 懒汉式 线程安全 不推荐使用
 */
public class Singleton5 {

    private static Singleton5 instance;

    private Singleton5() {

    }

    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized (Singleton5.class) {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
