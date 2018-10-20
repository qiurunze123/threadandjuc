package com.geek.threadandjuc.juc.Semaphore;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest1 {

    public static void main(String[] args) {
        //线城池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //只能五个线程同时访问（设置许可证数量），默认不公平
        //final Semaphore semp = new Semaphore(2)
        //强制公平  默认fasle 会影响性能
        final Semaphore semp = new Semaphore(2,true);

        //模拟多个客户端并发

        for (int index =0 ; index<=5;index ++){
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"尝试获取许可证");
                        semp.acquire();
                        System.out.println(Thread.currentThread().getName()+"获取许可证");
                        Thread.sleep(1000);
                        //访问完成后 释放 如果屏蔽下面的语句 则在控制台只能打印5条记录 之后线程一直阻塞
                        System.out.println(Thread.currentThread().getName()+"释放许可证");
                        semp.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(run);
        }
        executorService.shutdown();
    }
}
