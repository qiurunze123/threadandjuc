package com.geekagain.volatilego;

/**
 * @author 邱润泽 bullock
 */
public class ThreadVolatile2 extends Thread {

        public static void main(String[] args) throws Exception {
            ThreadVolatile2 thread=new ThreadVolatile2();
            System.out.println("Starting thread...");
            thread.start();
            Thread.sleep(3000);
            System.out.println("Asking thread to stop...");
            // 发出中断请求
            thread.interrupt();
            Thread.sleep(3000);
            System.out.println("Stopping application...");
        }


        @Override
        public void run() {
            //每隔一秒检测一下中断信号量
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("Thread is running!");
                long begin=System.currentTimeMillis();
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
