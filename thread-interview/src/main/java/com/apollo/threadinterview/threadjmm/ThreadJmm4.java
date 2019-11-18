package com.apollo.threadinterview.threadjmm;

/**
 * @author 邱润泽 bullock
 *
 * 解决 volatile
 */
public class ThreadJmm4 {
   int a = 1;
   int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b="+b+";a="+a);

        if(a==1&&b==3) {
            System.out.println("b=" + b + ";a=" + a+"===================================");
        }

    }

    public static void main(String[] args) {
        while (true) {
            ThreadJmm4 test = new ThreadJmm4();
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
