package com.geek.create;

/**
 * @author 邱润泽 bullock
 */
public class CreateThread1 {

    public static void main(String[] args) {
        Thread t = new Thread("你好");
        System.out.println(t.getName());
        t.start();
    }
}
