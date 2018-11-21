package com.geek.threadandjuc.base;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    static AtomicInteger i = new AtomicInteger();

    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int j = 0; j <100 ; j++) {
                i.incrementAndGet();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread[] ts =new Thread[10];
            for (int j = 0; j <10 ; j++) {
                ts[j] = new Thread(new AddThread());
            }
            for (int j = 0; j <10 ; j++) {
                ts[j].start();
            }

            for (int j = 0; j <10 ; j++) {
                ts[j].join();
            }

            System.out.println(i);
        }
    }
}
