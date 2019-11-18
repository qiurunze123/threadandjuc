package com.apollo.threadinterview.threadjmm;

/**
 * @author 邱润泽 bullock
 *
 * a=3  b=2
 * a=1   b=2
 * a=3    b=3
 *
 * 两个线程的通信是有一定的延迟和代价的 主从同步需要付出一定时间代价
 *
 * 可能出现 另一个线程看到一半的情况 比如只看到了b 没有看到a 的修改
 */
public class ThreadJmm3 {
   int a = 1;
   int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b="+b+";a="+a);
    }

    public static void main(String[] args) {
        while (true) {
            ThreadJmm3 test = new ThreadJmm3();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }

    }
}
