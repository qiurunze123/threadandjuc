package com.geek.errorsimple;

/**
 * @author 邱润泽 bullock
 *
 *  自动装箱 / 拆箱 每一次对象都是不同的值 来自动递增 多线程无法确定某一个对象
 */
public class IntegerLock {

    static  Integer  i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            for (int k = 0; k < 100000; k++) {
                synchronized (i) {
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread t1 = new AddThread();
        AddThread t2 = new AddThread();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
