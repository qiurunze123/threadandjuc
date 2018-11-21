package com.geek.threadandjuc.base;

public class DeakLock extends Thread{

    protected Object tool;

    static Object fork1 = new Object();

    static Object fork2 = new Object();

    public DeakLock(Object obj) {
        this.tool = obj;

        if(tool == fork1){
            this.setName("哲学家A");
        }

        if(tool == fork2) {
            this.setName("哲学家B");
        }

    }

    @Override
    public void run() {
        if(tool==fork1){
            synchronized (fork1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2){
                    System.out.println("哲学家A开始吃饭！！");
                }
            }
        };

        if(tool==fork2){
            synchronized (fork2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork1){
                    System.out.println("哲学家B开始吃饭！！");
                }
            }
        };


    }

    public static void main(String[] args) throws InterruptedException {
DeakLock A = new DeakLock(fork1);
        DeakLock B = new DeakLock(fork2);
        A.start();B.start();
        Thread.sleep(1000);
    }
}
