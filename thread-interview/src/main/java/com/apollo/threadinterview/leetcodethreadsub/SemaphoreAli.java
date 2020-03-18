package com.apollo.threadinterview.leetcodethreadsub;

import java.util.concurrent.Semaphore;

/**
 * @author 邱润泽 bullock
 */
public class SemaphoreAli {

    public static void main(String[] args) {


        Ali ali =new Ali(102);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ali.A(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("a");
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
                        ali.L(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("l");
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
                    ali.I(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("i\n");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

class Ali {
    private Semaphore sa1,sa2 ,sa3;
    private int n;

    public Ali(int n) {
        this.n = n;
        sa1 = new Semaphore(1);
        sa2 = new Semaphore(0);
        sa3 = new Semaphore(0);
    }

    public void A(Runnable printA) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                sa1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printA.run();
            sa2.release();
        }
    }

    public void L(Runnable printL) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                sa2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printL.run();
            sa3.release();
        }
    }

    public void I(Runnable printI) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try {
                sa3.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printI.run();
            sa1.release();
        }
    }
}