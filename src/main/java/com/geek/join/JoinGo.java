package com.geek.join;

/**
 * @author 邱润泽 bullock
 */
public class JoinGo {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {

            for (i = 0; i < 10000000; i++){

            } ;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        AddThread at = new AddThread();
        at.start();
        //等待的是主线程
        at.join();
        System.out.println(i);
    }

}
