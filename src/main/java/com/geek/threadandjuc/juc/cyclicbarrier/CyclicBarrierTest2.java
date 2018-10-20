package com.geek.threadandjuc.juc.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest2 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5);

        new Thread(new Wroker(barrier, "worker1")).start();
        new Thread(new Wroker(barrier, "worker2")).start();
        new Thread(new Wroker(barrier, "worker3")).start();
        new Thread(new Wroker(barrier, "worker4")).start();

        System.out.println("..............................");
        barrier.await();//这个的意思 主线程和分开的线程 都是用这个的线程
        System.out.println("所有线程工作完成！ main方法主线程继续执行！");
    }
}

    class Wroker implements  Runnable{

        //同步辅助类允许相互等待直到到达某个公共屏障点
        private CyclicBarrier barrier;

        private String name ;

        public Wroker(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(2000);
                System.out.println(name+"运行完毕！");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

}
