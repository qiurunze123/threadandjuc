package com.geek.threadandjuc.base;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable {

    static final CountDownLatch end = new CountDownLatch(10);
    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
         CountDownLatchDemo demo = new CountDownLatchDemo();
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i =0;i<10;i++){
            exec.submit(demo);
        }
        end.await();
        System.out.println("Fire!!!!");
        exec.shutdown();
    }
}
