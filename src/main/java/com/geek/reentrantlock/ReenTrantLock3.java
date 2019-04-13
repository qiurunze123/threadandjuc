package com.geek.reentrantlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 *
 * 构造 超时释放锁
 */
public class ReenTrantLock3 implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else{
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{lock.unlock();}
    }
    public static void main(String[] args) {
        ReenTrantLock3 tl=new ReenTrantLock3();
        Thread t1=new Thread(tl);
        Thread t2=new Thread(tl);
        t1.start();
        t2.start();
    }
}
