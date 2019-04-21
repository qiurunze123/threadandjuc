package com.geek.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 邱润泽 bullock
 *
 *
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{

        private String soldier;

        private final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic, String soldier) {
            this.soldier = soldier;
            this.cyclic = cyclic;
        }

        /**
         * CyclicBarrier的核心方法就只有一个await，它会抛出两个异常，InterruptedException和BrokenBarrierException。
         * InterruptedException显然是当前线程等待的过程被中断而抛出的异常，而这些要集合的线程有一个线程被中断就会导致线程永远都无法集齐，
         * 导致“栅栏损坏”，剩下的线程就会抛出BrokenBarrierException异常
         */
        @Override
        public void run() {
            try {
                //等待士兵到齐
                cyclic.await();
                //士兵开始做各自的工作
                doWork();
                //等待所有士兵完成任务
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + " 任务完成");
        }
    }

    /**
     * 这个类用于barrierAction
     */
    public static class BarrierRun implements Runnable{

        boolean flag;

        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if ( flag ){
                System.out.println("司令：[士兵" + N + "个, 任务完成!]");
            } else {
                System.out.println("司令：[士兵" + N + "个，集合完毕!]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        /**
         * 插入了BarrierRun作为barrierAction
         */
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));

        for ( int i = 0; i < N; i++ ){
            System.out.println("士兵 " + i + " 报道");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵 " + i));
            allSoldier[i].start();
            /**
             * 测试 栅栏损坏情况
             *
             * 得到1个InterruptedException和9个BrokenBarrierException
             */
//            if ( i == 5 ){
//                allSoldier[0].interrupt();
//            }
        }
    }
}
