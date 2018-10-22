package com.geek.threadandjuc.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolTest {
    public static void main(String[] args) {
        //core 核心线程数
        // max 最大线程数
        //timeout 超时时间
        //queue 等待队列
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i=0 ; i<=10 ;i++){
            final int index =i ;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(index*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+">>"+index);
                }
            });
        }
    }
}
