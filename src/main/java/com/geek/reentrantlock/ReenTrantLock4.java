package com.geek.reentrantlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 邱润泽 bullock
 *
 * 构造 超时释放锁
 */
public class ReenTrantLock4 implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(false);

    @Override
    public void run() {
        while(true){
            try{
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" 11111");
            }finally{
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenTrantLock4 r1 = new ReenTrantLock4();
        Thread t1=new Thread(r1,"Thread_t1");
        Thread t2=new Thread(r1,"Thread_t2");
        t1.start();t2.start();
    }
}
