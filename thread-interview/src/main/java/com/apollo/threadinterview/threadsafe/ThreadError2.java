package com.apollo.threadinterview.threadsafe;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/17
 *
 * 这个图俩个人都很绅士导致谁都不愿起身 就造成了死锁
 *
 * 死锁
 */
public class ThreadError2 implements Runnable{
    int flag = 1;

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        ThreadError2 deadLock1 = new ThreadError2();
        ThreadError2 deadLock2 = new ThreadError2();
        deadLock1.flag = 1;
        deadLock2.flag = 0;

        Thread t1 = new Thread(deadLock1);
        Thread t2 = new Thread(deadLock2);
        t1.start();
        t2.start();
    }

    @Override
    public void run() {

        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    //确保 第二个线程拿到锁
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //已经被2拿到
                synchronized (o2) {
                    System.out.println("线程1成功拿到俩把锁");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("线程2成功拿到俩把锁");
                }
            }
        }

    }
}
