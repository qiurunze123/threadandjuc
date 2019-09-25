package com.geekagain.threadsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock 死锁活锁问题
 */
public class ThreadSafeError4 {

    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (object1){
                        System.out.println(Thread.currentThread().getName()+"： 获取到了object1锁");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (object2){
                            System.out.println(Thread.currentThread().getName()+"： 获取到了object2锁");
                        }
                    }
                }
            }
        },"线程t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (object2){
                        System.out.println(Thread.currentThread().getName()+"： 获取到了object2锁");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (object1){
                            System.out.println(Thread.currentThread().getName()+"： 获取到了object1锁");
                        }
                    }
                }
            }
        },"线程t2").start();

    }

}
