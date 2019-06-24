package com.geek.lockthinkdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 邱润泽 bullock
 *
 * 循环比较直到成功为止 没有类似wait的阻塞
 *
 * 通过CAS操作完成自旋锁 -- A线程先进来调用myLock方法自己持有5秒钟 -- B进来后发现当前线程持有锁不为空 --
 * 只能通过自旋等待 -- 直到A释放锁后B随即抢到
 */
public class SpinLockDemo {

    /**
     * 对对象进行原子操作
     */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"名字");

        while(!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void unMyLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"##### invoke myunlock !");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                spinLockDemo.myLock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinLockDemo.unMyLock();
            }
        },"AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                spinLockDemo.myLock();
                spinLockDemo.unMyLock();
            }
        },"BB").start();

    }
}
