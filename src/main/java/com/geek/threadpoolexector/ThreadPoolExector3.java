package com.geek.threadpoolexector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 邱润泽 bullock
 */
public class ThreadPoolExector3 {

    public static void main(String[] args) {

        ExecutorService service1 = Executors.newSingleThreadExecutor();


        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是多线程!!");
            }
        });

        service1.execute(t1);
    }
}
