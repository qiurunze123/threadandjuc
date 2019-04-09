package com.geek.threadandjuc2;

/**
 * @author 邱润泽 bullock
 */
public class DeadLoackTest {

    public static void main(String[] args) {
        final OtherService otherService = new OtherService();
        final DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    deadLock.m1();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    otherService.s2();
                }
            }
        }.start();
    }
}
