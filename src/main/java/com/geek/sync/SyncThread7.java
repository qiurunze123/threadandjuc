package com.geek.sync;

/**
 * @author 邱润泽 bullock
 *
 *  修饰一个类 这个类的所有对象是一把锁
 */
public class SyncThread7 implements Runnable {

    private static int count;

    public SyncThread7() {
        count = 0;
    }

    public static void method() {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public synchronized void run() {
        method();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SyncThread7 syncThread1 = new SyncThread7();
        SyncThread7 syncThread2 = new SyncThread7();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }

}
