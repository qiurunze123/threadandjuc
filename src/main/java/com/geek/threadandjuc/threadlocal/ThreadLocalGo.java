package com.geek.threadandjuc.threadlocal;

/**
 *  使用ThreadLocal
 *  可以保证变量被每个线程度
 */
public class ThreadLocalGo {
    public static void main(String[] args) throws InterruptedException {

        final ThreadLocal<Integer> th = new ThreadLocal<Integer>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    th.set(100);
                    System.out.println("t1 set th="+th.get());
                    Thread.sleep(2000);
                    System.out.println("t1 get th="+th.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer ele = th.get();
                System.out.println("t2 get th="+ele);
                th.set(200);
                System.out.println("t2 get th="+th.get());
            }
        }).start();

    }
}
