package com.apollo.threadinterview.leetcodethreadsub.T04;

import java.util.concurrent.Semaphore;

/**
 * @author 邱润泽 bullock
 *
 * 现在有两种线程，氢 oxygen 和氧 hydrogen，你的目标是组织这两种线程来产生水分子。
 *
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 *
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 *
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 *
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 *
 * 换句话说:
 *
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 *
 */
public class H20Print {


    public static void main(String[] args) {
        H2O h2O= new H2O();

        for (int i = 0; i < 10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        h2O.hydrogen(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("H");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        for (int i = 0; i < 10 ; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        h2O.oxygen(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("O");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}


class H2O {

    private Semaphore H , HO;

    public H2O() {

        H = new Semaphore(2);
        HO = new Semaphore(0);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        //获得一个通行证走一个
        H.acquire();
        releaseHydrogen.run();
        HO.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {

        HO.acquire(2);
        releaseOxygen.run();
        H.release(2);

    }
}