package com.geek.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Condition1 implements Runnable {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition = reentrantLock.newCondition();

    @Override
    public void run() {
        reentrantLock.lock();
        try {
            System.out.println("我来了2222");
            condition.await();
            System.out.println("我来了111");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Condition1 condition1 = new Condition1();
        Thread thread = new Thread(condition1);
        thread.start();
        Thread.sleep(2000);
        reentrantLock.lock();
        condition.signal();
        reentrantLock.unlock();

    }
}
