package com.geek.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 */
public class ReenTrantLock1 implements Runnable {

    public static ReentrantLock reentrantLock = new ReentrantLock();

    public static  int i  = 0;


    @Override
    public void run() {
        for (int j = 0; j <10000 ; j++) {
            reentrantLock.lock();
            reentrantLock.lock();
            try {
                i++;
            }finally {
                reentrantLock.unlock();
                reentrantLock.unlock();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReenTrantLock1 tl=new ReenTrantLock1();
        Thread t1=new Thread(tl);
        Thread t2=new Thread(tl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
