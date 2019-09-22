package com.geekagain.threadsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock
 */
public class MuliThreadsError implements Runnable {


    static MuliThreadsError instance = new MuliThreadsError();

    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongIndex = new AtomicInteger();
    static  volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);

    static  volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    final boolean[] marked = new boolean[100000];
    public static void main(String[] args) throws InterruptedException {
       Thread t1 =  new Thread(instance);
        Thread t2 =  new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("表面上的结果：  "+instance.index);
        System.out.println("真正运行的结果：  "+realIndex.get());
        System.out.println("错误次数：  "+wrongIndex.get());

    }
    int index = 0 ;
    @Override
    public void run() {
        for (int i =0;i<10000;i++){

            marked[0]=true;
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            //俩个线程同时到这里
            synchronized (instance) {
                if (marked[index]&&marked[index-1]) {
                    System.out.println("发生错误 ：" + index);
                    wrongIndex.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }
}
