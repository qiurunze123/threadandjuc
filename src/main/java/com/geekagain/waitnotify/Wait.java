package com.geekagain.waitnotify;


/**
 * @author 邱润泽 bullock
 */
public class Wait {

    public static Object object = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            //只能由有一个线程执行
            synchronized (object) {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    //调用wait 会释放调锁
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println("线程"+Thread.currentThread().getName()+"notify");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread1();
        t1.start();
        Thread t2 =new Thread2();
        Thread.sleep(200);
        t2.start();
    }

}
