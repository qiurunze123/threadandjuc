package com.geekagain.threadsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock
 */
public class ThreadSafeError3 implements Runnable {

    static ThreadSafeError3 instance = new ThreadSafeError3();
    int index = 0;
    final boolean[] marked = new boolean[1000000];

    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();

    static volatile CyclicBarrier cyclicBarrier1 =new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier2 =new CyclicBarrier(2);


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上的结果为  ： " + instance.index);
        System.out.println("真正运行的次数  ： " + realIndex.get());
        System.out.println("错误次数  ： " + wrongCount.get());

        for (; ; ) {
            ;
        }
    }

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0; i < 10000; i++) {
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
            synchronized (instance) {//不仅保证原子性 还可以保证可见性
                if (marked[index]&&marked[index-1]) {
                    System.out.println("发生错误" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }
}
