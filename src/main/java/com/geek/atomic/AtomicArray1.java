package com.geek.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author 邱润泽 bullock
 */
public class AtomicArray1 {

    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int k = 0; k < 10000; k++) {
                atomicIntegerArray.getAndIncrement(k % atomicIntegerArray.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int k = 0; k < 10; k++) {
            ts[k] = new Thread(new AddThread());
        }
        for (int k = 0; k < 10; k++) {
            ts[k].start();
        }
        for (int k = 0; k < 10; k++) {
            ts[k].join();
        }
        System.out.println(atomicIntegerArray);
    }
}
