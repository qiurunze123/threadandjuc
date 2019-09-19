package com.geekagain.threadcatch;

/**
 * @author 邱润泽 bullock
 */
public class ThreadCatchProcess4 implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(new ThreadCatchProcess3("获取异常"));

        new Thread(new ThreadCatchProcess4(), "MyThread-1").start();
        Thread.sleep(300);
        new Thread(new ThreadCatchProcess4(), "MyThread-2").start();
        Thread.sleep(300);
        new Thread(new ThreadCatchProcess4(), "MyThread-3").start();
    }


    @Override
    public void run() {
        throw new RuntimeException();
    }
}
