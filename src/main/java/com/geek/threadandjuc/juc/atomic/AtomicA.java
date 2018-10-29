package com.geek.threadandjuc.juc.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * atomic类不能保证成员方法的原子性
 */
public class AtomicA implements Runnable {

    //原子类
    private static AtomicInteger sum = new AtomicInteger(0);

    //如果add方法是原子性的,那么每次的结果都是10的整数倍
    /*synchronized*/
    public synchronized static void add(){
        sum.addAndGet(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum.addAndGet(9);
        System.out.println(sum);
    }

    @Override
    public void run() {
        add();
    }

    public static void main(String[] args) {
        //10个线程调用，每个线程得到10的倍数， 最终结果应该为100，才是正确的
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            es.submit(new AtomicA());
        }
        es.shutdown();
    }
}
