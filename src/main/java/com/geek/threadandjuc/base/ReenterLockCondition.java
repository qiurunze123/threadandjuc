package com.geek.threadandjuc.base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();
    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("thread is goding on!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition t1 = new ReenterLockCondition();
        Thread t2 = new Thread(t1);
        t2.start();
        Thread.sleep(2000);
        //通知线程继续执行
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
