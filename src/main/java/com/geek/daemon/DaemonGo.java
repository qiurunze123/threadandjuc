package com.geek.daemon;

/**
 * @author 邱润泽 bullock
 */
public class DaemonGo {

    public static class DaemonT extends Thread{
        @Override
        public void run(){
            while(true){
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t=new DaemonT();
        //设为守护线程 主线程结束后他也会结束
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
}
