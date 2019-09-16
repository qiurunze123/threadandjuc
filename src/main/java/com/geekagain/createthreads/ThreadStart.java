package com.geekagain.createthreads;

/**
 * @author 邱润泽 bullock
 *使用 thread 创建线程
 */
public class ThreadStart extends Thread{

    @Override
    public void run() {
        System.out.println("use Thread 打印线程");
    }

    public static void main(String[] args) {
        new ThreadStart().start();
    }
}
