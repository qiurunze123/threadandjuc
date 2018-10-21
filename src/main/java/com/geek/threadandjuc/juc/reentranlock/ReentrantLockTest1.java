package com.geek.threadandjuc.juc.reentranlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest1 {


    public static void main(String[] args) {
        final ReentrantLockTest1 ur = new ReentrantLockTest1();

        Thread t1 =new Thread(new Runnable() {
            @Override
            public void run() {
                ur.run1();
            }
        },"t1");




        Thread t2 =new Thread(new Runnable() {
            @Override
            public void run() {
                ur.run2();
            }
        },"t1");

        t1.start();
        t2.start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private Lock lock = new ReentrantLock();

    public void run1() {

        try {
            lock.lock();
            System.out.println("当前线程" + Thread.currentThread().getName() + "进入run1......");
            Thread.sleep(1000);
            System.out.println("当前线程" + Thread.currentThread().getName() + "退出run1......");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


        public void run2() {

            try {
                lock.lock();
                System.out.println("当前线程" + Thread.currentThread().getName() + "进入run2......");
                Thread.sleep(1000);
                System.out.println("当前线程" + Thread.currentThread().getName() + "退出run2......");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
