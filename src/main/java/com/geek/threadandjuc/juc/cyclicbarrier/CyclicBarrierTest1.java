package com.geek.threadandjuc.juc.cyclicbarrier;


import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierTest1 {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Thread(new Runnre(barrier, "一号选手")));
        executor.submit(new Thread(new Runnre(barrier, "二号选手")));
        executor.submit(new Thread(new Runnre(barrier, "三号选手")));
        executor.shutdown();
    }
}


    class Runnre implements Runnable{

        //同步辅助类允许相互等待直到到达某个公共屏障点
        private CyclicBarrier barrier;

        private String name ;

        public Runnre(CyclicBarrier barrier, String name) {
            super();
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000*(new Random()).nextInt(8));
                System.out.println(name+"准备好了。。。。。");
                //barrier的await方法 在所有参与者 都在此barrier上 调用await方法   方法之前一直等待
                barrier.await();
                //设置等待时间 如果等待了一秒 线程还没有就位 则自己继续运行 但是会导致barrier被标记为一个已经被破坏的barrier
                barrier.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println(name+"中断异常");
            } catch (BrokenBarrierException e) {
                System.out.println(name+"Barrier损坏异常！");
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            System.out.println(name+"起跑！");
        }

}
