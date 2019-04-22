package com.geek.threadandjuc.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest2 {

    public static void main(String[] args) {

    final CountDownLatch countDownLatch3 = new CountDownLatch(3);
    final CountDownLatch countDownLatch2 = new CountDownLatch(2);



        new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("T2 START WAITTING FOR COUNTDownLatch to zreo");
                countDownLatch2.await();
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
                countDownLatch3.await();
                System.out.println("T3 stop ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    },"t2").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("countdown 3");
                    countDownLatch3.countDown();

                    Thread.sleep(1000);
                    System.out.println("countdown 2");
                    countDownLatch2.countDown();

                    Thread.sleep(1000);
                    System.out.println("countdown 3");
                    countDownLatch3.countDown();
                    Thread.sleep(1000);
                    System.out.println("countdown 2");
                    countDownLatch2.countDown();

                    Thread.sleep(1000);
                    System.out.println("countdown 3");
                    countDownLatch3.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }
}
