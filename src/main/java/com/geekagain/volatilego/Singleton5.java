package com.geekagain.volatilego;

/**
 * @author 邱润泽 bullock
 */
public class Singleton5 {

    private static Singleton5 instance;

    private Singleton5() {

    }

    public static Singleton5 getInstance() {
        if (instance == null) {
            synchronized ("lock") {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
