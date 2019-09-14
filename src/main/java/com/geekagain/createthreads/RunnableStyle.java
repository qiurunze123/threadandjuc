package com.geekagain.createthreads;

/**
 * @author 邱润泽 bullock
 */
public class RunnableStyle implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }
    @Override
    public void run() {
        System.out.println("使用runnable创建线程");
    }
}
