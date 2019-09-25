package com.geekagain.volatilego;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock
 */
public class UserVolatile implements Runnable{

    volatile boolean done = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Runnable r =  new UserVolatile();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(((UserVolatile) r).done);
        System.out.println(((UserVolatile) r).realA.get());
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            setDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        done = true;
    }
}
