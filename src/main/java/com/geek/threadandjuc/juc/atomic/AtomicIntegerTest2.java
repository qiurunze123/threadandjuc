package com.geek.threadandjuc.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 输出打印结果值
 */
public class AtomicIntegerTest2 {

    public static AtomicInteger count = new AtomicInteger(0) ;

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count.getAndIncrement();
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("AtomicInteger count:" + count);
    }

}
