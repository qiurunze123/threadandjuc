package com.geekagain.waitnotify.Print;

/**
 * @author 邱润泽 bullock
 *
 * 1.拿到锁就打印
 * 2.打印完唤醒其他线程
 *   避免资源浪费
 */
public class PrintOddEvenTwoThreadVersion2 {


    public static void main(String[] args) {
        new Thread(new EvenAddRunnable(),"偶数").start();
        new Thread(new EvenAddRunnable(),"奇数").start();

    }

    private static  int count;

    private static Object object = new Object();

    static class EvenAddRunnable implements Runnable {

        @Override
        public void run() {
            while (count <= 100) {
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + count);
                    count++;
                    object.notify();
                    if (count <= 100) {
                        try {
                            //如果任务还没奇数就让出当前线程 自己就休眠
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
