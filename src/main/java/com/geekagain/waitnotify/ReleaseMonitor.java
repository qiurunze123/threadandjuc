package com.geekagain.waitnotify;

/**
 * @author 邱润泽 bullock
 */
public class ReleaseMonitor {

    private static Object object1 = new Object();
    private static Object object2 = new Object();


    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object1) {
                    System.out.println("我获得A锁");
                    synchronized (object2) {
                        System.out.println("我获得B锁");
                        try {
                            System.out.println("我调用wait  释放  A锁");
                            object1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println("我是线程2 获得A锁");
                    synchronized (object2) {
                        System.out.println("我是线程2 获得B 锁");
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }



}
