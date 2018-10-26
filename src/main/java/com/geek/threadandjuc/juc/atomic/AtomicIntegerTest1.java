package com.geek.threadandjuc.juc.atomic;

/**
 * 打印结果，在多线程的环境下是不对的
 */
public class AtomicIntegerTest1 {

    public static int count = 0 ;

    public static void main(String[] args) {
        for (int i =0 ; i<1000 ; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count++;
                }
            }).start();
        }
        System.out.println("count:"+count);
    }
}
