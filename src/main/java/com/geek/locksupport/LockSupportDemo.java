package com.geek.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 邱润泽 bullock
 *
 * thread线程调用LockSupport.park()致使thread阻塞，当mian线程睡眠3秒结束后通过LockSupport.unpark(thread)方法唤醒thread线程,
 * thread线程被唤醒执行后续操作。另外，还有一点值得关注的是，LockSupport.unpark(thread)可以指定线程对象唤醒指定的线程。
 *
 * 作者：你听___
 * 链接：https://www.jianshu.com/p/9677a754cf60
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            }
        });
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(thread);
    }
}
