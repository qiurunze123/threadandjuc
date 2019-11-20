package com.apollo.threadinterview.threadandobject;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/17
 *
 * 当我们需要线程休息一下 的时候 wait notify 等就派上了场
 * 当线程调用wait方法 的时候调用者本身就会进入 阻塞状态不在参与线程的调度直到以下几种情况发生 才会被唤醒：
 *
 * 另一个线程调用这个对象的notify（）方法切刚好被唤醒的是本线程
 * 另一个线程调用的这个对象的notifyall（）方法
 * 过了wait（long timeout）规定的超过时间 如果传入0 就是永久等待
 * 线程自身调用了interrupt()方法
 *
 * ==========================================================
 * 唤醒阶段
 * notify 是唤醒一个 notifyall 唤醒多个
 * 例子：  有四个线程 第四个获得了锁 如果使用notify那么就会选取一个被唤醒 如果使用notifyall 那么就会被唤醒多个
 * 至于哪个会抢到monitor锁那么就要看线程的调度了
 *
 * ===========================================================
 *
 * 特殊情况（遇到中断）
 *
 * 加入一个线程使用了wait方法 但是 自身中断了 那么他就会抛出interrupt异常 并释放掉当前的锁
 * ====================================================================
 *
 * 大家思考下面代码俩个问题??
 *
 * 1.代码执行顺序
 * 2.wait会释放掉monitor锁吗
 *
 * */
public class WaitAndNotify1 {

    public static Object object = new Object();

    static class Thread0 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "开始执行了===");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁。");
            }
        }
    }

    static class Thread1 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify()");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread0 thread0 = new Thread0();
        Thread1 thread1 = new Thread1();
        //保证notify先执行
        thread0.start();
        Thread.sleep(200);
        thread1.start();
    }
}
