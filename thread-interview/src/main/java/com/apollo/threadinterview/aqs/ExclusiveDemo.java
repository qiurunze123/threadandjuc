package com.apollo.threadinterview.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author 邱润泽 bullock
 *
 * 它在同一时刻只允许一个线程占有锁。ExclusiveDemo定义了一个静态内部类，该内部类继承了同步器并实现了独占式获取和释放同步状态。
 * 在tryAcquire方法中，通过CAS方式设置同步器状态，如果设置成功，返回true，
 * 设置失败返回false。tryRelease(int arg)方法是将同步器状态设置为0
 *
 * 我们并没有直接和AbstractQueuedSynchronizer 而是 调用ExclusiveDemo提供的方法
 *
 */
public class ExclusiveDemo implements Lock {
    // 静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        // 是否处于独占状态
        @Override
        protected boolean isHeldExclusively() {
            return this.getState() == 1;
        }

        // 当状态为0时，获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁，将状态设置为0
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        // 返回一个Condition,没给Condition都包含了一个Condition队列
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(0);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
