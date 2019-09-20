package com.geekagain.yieldgo;

/**
 * @author 邱润泽 bullock
 */
public class YieldThread extends Thread {

    public YieldThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("" + this.getName() + "-----" + i);
            // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i == 5) {
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
            YieldThread ta=new YieldThread("hello1");
            YieldThread tb=new YieldThread("hello2");
            ta.start();
            tb.start();
        }
    }