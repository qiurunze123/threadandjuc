package com.geekagain.waitnotify.Print;

/**
 * @author 邱润泽 bullock
 */
public class PrintOddEvenTwoThread {

    private static int count;

    private static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (count<100){
                    synchronized (lock){
                        if((count & 1 ) == 0){
                            System.out.println(Thread.currentThread().getName()+"  :"+count);
                            count++;
                        }
                    }
                }
            }
        },"Thread - 偶数").start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count<100){
                    synchronized (lock){
                        if((count & 1 ) != 0){
                            System.out.println(Thread.currentThread().getName()+"  :"+count);
                            count++;
                        }
                    }
                }
            }
        },"Thread - 奇数").start();


    }
}
