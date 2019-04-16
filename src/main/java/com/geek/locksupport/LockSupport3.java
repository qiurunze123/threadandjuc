package com.geek.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 邱润泽 bullock
 *
 * 模拟打断
 */
public class LockSupport3 {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name){
            super.setName(name);
        }
        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in "+getName());
                LockSupport.park();
                System.out.println("中断标志========"+Thread.interrupted());
                if(Thread.interrupted()){
                    System.out.println(getName()+" 被中断了");
                }
            }
            System.out.println(getName()+"执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
