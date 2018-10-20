package com.geek.threadandjuc.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest1 {
    public static void main(String[] args) {

        final CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("T2 START WAITTING FOR COUNTDownLatch to zreo");
                    countDownLatch.await();
                    System.out.println("T2 stop ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("T3 START WAITTING FOR COUNTDownLatch to zreo");
                    countDownLatch.await();
                    System.out.println("T3 stop ");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t3").start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("countdown 1");
                    countDownLatch.countDown();

                    Thread.sleep(1000);
                    System.out.println("countdown 2");
                    countDownLatch.countDown();

                    Thread.sleep(1000);
                    System.out.println("countdown 3");
                    countDownLatch.countDown();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }
}
