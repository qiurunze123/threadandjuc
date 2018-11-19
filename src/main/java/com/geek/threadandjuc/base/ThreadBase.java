package com.geek.threadandjuc.base;

public class ThreadBase {

    public static void main(String[] args) {
        Thread a1 = new Thread(){

            @Override
            public void run(){
                System.out.println("base----->>thread");
            }
        };
        a1.start();
    }
}
