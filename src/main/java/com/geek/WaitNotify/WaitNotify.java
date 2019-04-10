package com.geek.WaitNotify;

/**
 * @author 邱润泽 bullock
 */
public class WaitNotify {

    final static Object object = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T1 start! ");
                try {
                    System.out.println(System.currentTimeMillis() + ":T1 wait for object ");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
//        Thread t1_1 = new T1() ;
//        t1_1.start();
        t1.start();
        t2.start();
    }
}
