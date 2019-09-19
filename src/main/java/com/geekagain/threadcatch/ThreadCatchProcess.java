package com.geekagain.threadcatch;

/**
 * @author 邱润泽 bullock
 */
public class ThreadCatchProcess implements Runnable {


    public static void main(String[] args)  throws InterruptedException {
        try {
            new Thread(new ThreadCatchProcess(), "MyThread-1").start();
            Thread.sleep(1000);
            new Thread(new ThreadCatchProcess(), "MyThread-2").start();
            Thread.sleep(1000);
            new Thread(new ThreadCatchProcess(), "MyThread-3").start();
        } catch (RuntimeException e) {
            System.out.println("主线程抛出异常.  ");
        }

    }

    @Override
    public void run() {
        try {
            throw new Exception("我来抛出异常 看看对别人有影响没!");
        } catch (Exception e) {
            System.out.println("子线程抛出异常.");
        }
    }
}
