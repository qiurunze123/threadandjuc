package com.geekagain.threadstatechange;

/**
 * @author 邱润泽 bullock
 * 详细解读 new runnable running terminated
 *
 * six state change
 */
public class ThreadStateChange implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadStateChange());
        //new
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //RUNNABLE的状态，即使是正在运行，也是RUNNABLE 而不是RUNNING 外在表现是runnable 实际内部有一个变化
        System.out.println(thread.getState());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println("我来打印"+i);
        }
    }

}
