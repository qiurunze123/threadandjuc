package com.apollo.threadinterview.leetcodethreadsub.T01;

import java.util.concurrent.CountDownLatch;

/**
 * @author 邱润泽 bullock
 * <p>
 * 三个不同的线程将会共用一个 PrintInTurn 实例。
 * <p>
 * 线程 A 将会调用 one() 方法
 * 线程 B 将会调用 two() 方法
 * 线程 C 将会调用 three() 方法
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
 */
public class PrintInTurn {


    public static void main(String[] args) {

        PrintInturnTherad ad = new PrintInturnTherad();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ad.first(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("我来打印1");
                    }
                });
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ad.second(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("我来打印2");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ad.third(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("我来打印3");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t2.start();
        t1.start();
        t3.start();
    }


}

class PrintInturnTherad {

    private CountDownLatch countDownLatchA;
    private CountDownLatch countDownLatchB;

    public PrintInturnTherad() {
        countDownLatchA = new CountDownLatch(1);
        countDownLatchB = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) {
        printFirst.run();
        countDownLatchA.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        countDownLatchA.await();
        printSecond.run();
        countDownLatchB.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // printThird.run() outputs "third". Do not change or remove this line.
        countDownLatchB.await();
        printThird.run();
    }

}