package com.apollo.threadinterview.threadbase;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/16
 */
public class ThreadStyle  extends Thread{

    @Override
    public void run() {
        System.out.println("用thread类实现线程");
    }
}
