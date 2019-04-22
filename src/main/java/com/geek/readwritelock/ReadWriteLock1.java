package com.geek.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 产生死锁，因为同一个线程中，在没有释放读锁的情况下，就去申请写锁，这属于锁升级，ReentrantReadWriteLock是不支持的
 */
public class ReadWriteLock1 {

    public static void main(String[] args) {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.readLock().lock();
        System.out.println("get readLock.");
        rtLock.writeLock().lock();
        System.out.println("blocking");
    }
}
