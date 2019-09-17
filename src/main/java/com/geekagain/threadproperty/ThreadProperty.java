package com.geekagain.threadproperty;

/**
 * @author 邱润泽 bullock
 */
public class ThreadProperty {

    public static void main(String[] args) {
        Thread thread =new Thread();
        System.out.println("主线程的ID:"+Thread.currentThread().getId());
        System.out.println("子线程ID:"+thread.getId());
    }
}
