package com.apollo.threadinterview.threadsafe;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/17
 *
 * 运行结果出错
 */

public class ThreadError1 implements Runnable{


    static ThreadError1 instance = new ThreadError1();
    int  index = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上的结果为  ： "+instance.index);
        for(;;) {
            ;
        }
    }

    @Override
    public void run() {
        for(int i=0 ;i<10000;i++){
            index++;
        }
    }
}
