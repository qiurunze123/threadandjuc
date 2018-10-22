package com.geek.threadandjuc.juc.Reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest1 {

    //并发读 不会加锁 速写 写写都会加锁

    private ReentrantReadWriteLock rwLock =new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();


    private void read() {
        try {
            readLock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "read进入....");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "read退出....");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }


        private void write(){
            try {
                readLock.lock();
                System.out.println("当前线程："+Thread.currentThread().getName()+"write进入....");
                Thread.sleep(3000);
                System.out.println("当前线程："+Thread.currentThread().getName()+"write退出....");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                readLock.unlock();
            }
    }


    public static void main(String[] args) {
        final ReadWriteLockTest1 urrw = new ReadWriteLockTest1();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                urrw.read();
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                urrw.read();
            }
        },"t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                urrw.write();
            }
        },"t3");

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                urrw.write();
            }
        },"t4");

//        t1.start();
//        t2.start();

//        t1.start();
//        t3.start();
//
        t3.start();
        t4.start();


    }


}
