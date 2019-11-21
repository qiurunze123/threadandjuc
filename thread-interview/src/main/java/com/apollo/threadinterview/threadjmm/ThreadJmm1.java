package com.apollo.threadinterview.threadjmm;

/**
 * @author 邱润泽 bullock
 * 演示重排序
 * 一共有三种情况
 *
 * 1 a=1;x=b(0) ; b=1;y=a(1) 最终结果是x=0 y=1
 * 2 b=1;y=a(0) ; a=1;x=b(1) 最终结果是x=1 y=0 将线程1和2 调换下顺序会有大概率发生
 * 3 b=1;a=1;   x=b(1) ;y=a(1) 最终结果是x=1 y=1 请看threadjmm2
 */
public class ThreadJmm1 {


    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a=1;
                x=b;
            }
        });

        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                b=1;
                y=a;
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.println("x = "+x+",y = "+y);
    }
}
