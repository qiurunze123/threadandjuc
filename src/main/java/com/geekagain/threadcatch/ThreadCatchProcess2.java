package com.geekagain.threadcatch;

/**
 * @author 邱润泽 bullock
 */
public class ThreadCatchProcess2 implements Runnable {


    public static void main(String[] args) {
        new Thread(new ThreadCatchProcess2()).start();
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }

    }

    @Override
    public void run() {
        throw new RuntimeException("我来抛出异常 看看对别人有影响没!");
    }
}
