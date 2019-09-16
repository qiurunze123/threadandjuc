package com.geekagain.volatilego;

/**
 * @author 邱润泽 bullock
 */
public class ThreadVolatile extends Thread {

    //线程中断信号量
    volatile boolean stop = false;

    public static void main(String[] args) throws Exception {
        ThreadVolatile thread = new ThreadVolatile();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // 设置中断信号量
        thread.stop = true;
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }


    @Override
    public void run() {
        //每隔一秒检测一下中断信号量
        while (!stop) {
            System.out.println("Thread is running!");
            long begin = System.currentTimeMillis();
            /**
             * 使用while循环模拟sleep方法，这里不要使用sleep，否则在阻塞时会抛InterruptedException异常而退出循环，
             * 这样while检测stop条件就不会执行，失去了意义。
             */
            while ((System.currentTimeMillis() - begin < 1000)) {

            }
        }
        System.out.println("Thread exiting under request!");
    }
}
