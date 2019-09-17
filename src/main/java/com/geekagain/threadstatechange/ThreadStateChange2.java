package com.geekagain.threadstatechange;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 邱润泽 bullock
 * 详细解读
 * TIMED_WAITING
 * BLOCKED
 * WAITING
 * six state change
 */
public class ThreadStateChange2 implements Runnable {

    public static void main(String[] args) {
        ThreadStateChange2 stateChange2 = new ThreadStateChange2();

        Thread thread1 = new Thread(stateChange2);
        thread1.start();
        Thread thread2 = new Thread(stateChange2);
        thread2.start();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印出Timed_Waiting状态，因为正在执行Thread.sleep(1000);
        System.out.println(thread1.getState());
        //打印出BLOCKED状态，因为thread2想拿得到sync()的锁却拿不到
        System.out.println(thread2.getState());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印出WAITING状态，因为执行了wait()
        System.out.println(thread1.getState());

    }

    @Override
    public void run() {
        synchronized (this) {
            try {

                // Thread.sleep(2000);
                // Nanos toNanos 时间
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
