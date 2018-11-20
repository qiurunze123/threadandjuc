package com.geek.threadandjuc.base;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockBase implements Runnable{

        public static ReentrantLock lock = new ReentrantLock();
        public static int i =0;
        @Override
        public void run() {
        for (int j=0;j<10000000;j++){
            i++;
            lock.lock();
            try {
                i++;
            }  finally {
                lock.unlock();
            }
        }
        }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockBase t1 = new ReenterLockBase();
        Thread t3 = new Thread(t1);
        Thread t2 = new Thread(t1);
        t2.start();
        t3.start();
        t2.join();
        t3.join();
        System.out.println(i);
    }
}
