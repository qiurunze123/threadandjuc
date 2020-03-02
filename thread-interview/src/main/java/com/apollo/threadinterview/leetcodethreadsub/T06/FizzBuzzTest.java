package com.apollo.threadinterview.leetcodethreadsub.T06;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author 邱润泽 bullock
 */
public class FizzBuzzTest {

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(100);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.number();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fizzBuzz.fizz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("fizz");
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
                    fizzBuzz.fizzbuzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("fizzbuzz");
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
                    fizzBuzz.buzz(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("buzz");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}

class FizzBuzz {


    private Semaphore a, b, c, d;
    private int n;

    public FizzBuzz(int n) {
        this.n = n;
        a = new Semaphore(0);
        b = new Semaphore(0);
        c = new Semaphore(0);
        d = new Semaphore(1);
    }

    public void fizz(Runnable printFizz) throws InterruptedException {

        for (int i = 3; i < n; i += 3) {
            if (i % 5 != 0) {
                a.acquire();
                printFizz.run();
                d.release();
            }


        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i = i + 5) {
            if (i % 3 != 0) {
                b.acquire();
                printBuzz.run();
                d.release();
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i = i + 15) {
            c.acquire();
            printFizzBuzz.run();
            d.release();
        }

    }

    public void number() throws InterruptedException {


        for (int i = 1; i < n; i++) {

            d.acquire();

            if (i % 3 == 0 && i % 5 == 0) {
                c.release();
            } else if (i % 3 == 0) {
                a.release();
            } else if (i % 5 == 0) {
                b.release();
            } else {
                d.release();
            }


        }
    }
}