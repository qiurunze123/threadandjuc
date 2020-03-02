package com.apollo.threadinterview.leetcodethreadsub.T03;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author 邱润泽 bullock
 */
public class ZoreOddEven {

    public static void main(String[] args) {

        ZeroEvenOdd  zeroEvenOdd =new ZeroEvenOdd(3);

        IntConsumer intConsumer = new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value+"====================");
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.zero(intConsumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.odd(intConsumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zeroEvenOdd.even(intConsumer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

class ZeroEvenOdd {
    private int n;
    //0 - 偶数 -  奇数
    private Semaphore zero,even,odd;

    public ZeroEvenOdd(int n) {
        this.n = n;
        zero = new Semaphore(1);
        even = new Semaphore(0);
        odd = new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {

        for (int i = 0; i < n  ; i++) {

            zero.acquire();
            printNumber.accept(0);
            //就是判断n的二进制表示最后一位是否为1，是的话就是奇数，否则偶数 或者 i%2 都行
            if((i%1) == 0 ){
            odd.release();
            }else{
                even.release();
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <=n  ; i+=2) {

            even.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {

        for (int i = 1; i <=n; i+=2) {

            odd.acquire();
            printNumber.accept(i);
            zero.release();
        }
    }
}