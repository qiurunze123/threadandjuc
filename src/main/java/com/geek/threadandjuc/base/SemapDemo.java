package com.geek.threadandjuc.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class SemapDemo implements Runnable {
    final Semaphore semp = new Semaphore(5);//资源许可
    @Override
    public void run() {
        try {
            semp.acquire();
        //模拟耗时操作
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getId()+":Done!");
        semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService   exec = Executors.newFixedThreadPool(5);
        final SemapDemo demo  =new SemapDemo();
        for (int i=0;i<20;i++){
            exec.submit(demo);
        }

    }
}
