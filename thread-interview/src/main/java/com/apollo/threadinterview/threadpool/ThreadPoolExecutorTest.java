package com.apollo.threadinterview.threadpool;

import java.util.concurrent.*;

/**
 * @author 邱润泽 bullock
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(5);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10,
                60, TimeUnit.SECONDS, queue,handler);

        for (int i = 0; i <16 ; i++) {
            threadPool.execute(new Thread(new ThreadTest(),"线程"+i));
             System.out.println("线程池中活跃的线程数： " + threadPool.getPoolSize());

            if(queue.size()>0){
                System.out.println("-----队列中的阻塞线程数"+ queue.size());
            }
        }

        threadPool.shutdown();


    }
}
