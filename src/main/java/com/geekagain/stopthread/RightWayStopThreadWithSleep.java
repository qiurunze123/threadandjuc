package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock 带有sleep 中断线程得写法
 */
public class RightWayStopThreadWithSleep {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int num =0;
                while(num<=300&&!Thread.currentThread().isInterrupted()){
                    if(num%100 == 0){
                        System.out.println( "we is  100 multiple");
                    }
                    num++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread =new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

    }
}
