package com.geek.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * ReentrantReadWriteLock支持锁降级，上面代码不会产生死锁。这段代码虽然不会导致死锁，但没有正确的释放锁。
 * 从写锁降级成读锁，并不会自动释放当前线程获取的写锁，仍然需要显示的释放，否则别的线程永远也获取不到写锁
 *
 */
public class ReadWriteLock2 {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        reentrantReadWriteLock.writeLock().lock();

        System.out.println("get write lock !");

        reentrantReadWriteLock.readLock().lock();

        System.out.println("get read lock !");
    }
}
