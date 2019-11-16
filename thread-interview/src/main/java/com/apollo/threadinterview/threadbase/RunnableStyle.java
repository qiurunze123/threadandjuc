package com.apollo.threadinterview.threadbase;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/16
 */
public class RunnableStyle implements Runnable {

    @Override
    public void run() {
        System.out.println("用runnable接口实现线程方式");
    }
}
