package com.geek.sync;

/**
 * @author 邱润泽 bullock
 *
 * 修饰一个方法 synchronized修饰方法和修饰一个代码块类似，只是作用范围不一样
 * ，修饰代码块是大括号括起来的范围，而修饰方法范围是整个函数
 * 。如将【SyncThread】中的run方法改成如下的方式，实现的效果一样。
 *
 * 写法一修饰的是一个方法，写法二修饰的是一个代码块，但写法一与写法二是等价的，都是锁定了整个方法时的内容。
 *
 */
public class SyncThread5 implements Runnable {

    private static int count;

    public SyncThread5() {
        count = 0;
    }

    @Override
    public synchronized void run() {
        /**
         * 同一时刻只有一个线程执行 其他线程受阻
         */
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SyncThread syncThread = new SyncThread();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();
    }


}
