package com.executor.ThreadPoolCodeTest;

/**
 * @author 邱润泽 bullock
 */
public class Task implements Runnable {
    private int num;

    public Task(int i){
        this.num = i;
    }

    @Override
    public void run() {
        System.out.println("执行当前任务的线程是："+Thread.currentThread().getName());
        System.out.println("我是任务:"+num+"我在执行...");
    }

}
