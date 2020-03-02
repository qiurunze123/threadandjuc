package com.apollo.threadinterview.leetcodethreadsub.T01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 邱润泽 bullock
 */
public class SemaphoreInTurn {

    public static void main(String[] args) {

        Foo03 ad = new Foo03();

        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ad.first(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("我来打印1");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.execute(new Runnable() {
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

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ad.first(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("我来打印1");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

class Foo03{

    private Semaphore spa,spb;

    public Foo03(){
        spa = new Semaphore(0);
        spb = new Semaphore(0);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        //只有等first线程释放Semaphore后使Semaphore值为1,另外一个线程才可以调用（acquire）
        spa.release();
    }
    public void second(Runnable printSecond) throws InterruptedException {
        //只有spa为1才能执行acquire，如果为0就会产生阻塞
        spa.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        spb.release();
    }
    public void third(Runnable printThird) throws InterruptedException {
        //只有spb为1才能通过，如果为0就会阻塞
        spb.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }


}
