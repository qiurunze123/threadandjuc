package com.apollo.threadinterview.leetcodethreadsub.T02;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author 邱润泽 bullock
 */
public class PrintEvery {

    public static void main(String[] args) {


        FooBar fooBar =new FooBar(3);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fooBar.foo(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("打印1");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fooBar.bar(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("打印2");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

}

class FooBar {
    private Semaphore sa1,sa2 ;
    private int n;

    public FooBar(int n) {
        this.n = n;
        sa1 = new Semaphore(1);
        sa2 = new Semaphore(0);
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                sa1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printFoo.run();
            sa2.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                sa2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printBar.run();
            sa1.release();
        }
    }
}