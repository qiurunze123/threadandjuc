package com.apollo.threadinterview.aqs.reentrantlockmind;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 */
public class ReentrantLockMind {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
}
