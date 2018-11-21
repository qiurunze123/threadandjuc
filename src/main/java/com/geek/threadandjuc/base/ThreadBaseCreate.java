package com.geek.threadandjuc.base;

public class ThreadBaseCreate implements Runnable{
    @Override
    public void run() {
        System.out.println("我是runable接口实现的！");
    }

    public static void main(String[] args) {
        Thread a2 = new Thread(new ThreadBaseCreate());
        a2.start();

    }
}
