package com.geek.threadandjuc.juc.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  * 可重入锁 -- 利用 -- Condition -- 打印1到9这9个数字，由A线程先打印1，2，3，然后由B线程打印4,5,6，然后再由A线程打印7，8，9.
 */
public class ReentrantLockTest3 {

    static class numD {
        private volatile int num = 1;
    }

    public static void main(String[] args) {

        final Lock lock =new ReentrantLock();

        final Condition condition1 = lock.newCondition();

        final Condition condition2 = lock.newCondition();

        final numD numd= new numD();

        Thread a1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                lock.lock();
                while (numd.num<=3){
                    System.out.println("我是线程A打印======"+numd.num);
                    numd.num++;
                }
                condition1.signal();

                condition2.await();
                while (numd.num<=9){
                    System.out.println("我是线程C打印======"+numd.num);
                    numd.num++;
                }

            }catch (Exception e){
                }finally {
                    lock.unlock();
                }
            }
        });

        Thread a2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                lock.lock();
                if(numd.num<=3){
                    condition1.await();
                }
                while (numd.num<=6){
                    System.out.println("我是线程B打印======="+numd.num);
                    numd.num++;
                }
                    condition2.signal();

                }catch (Exception e){
                }finally {
                    lock.unlock();
                }
            }
        });

a1.start();
a2.start();

    }
}
