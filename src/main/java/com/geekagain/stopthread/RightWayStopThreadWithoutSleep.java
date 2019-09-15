package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 *
 * 没有 sleep 或者wait 方法如何停止线程
 */
public class RightWayStopThreadWithoutSleep implements Runnable{
    @Override
    public void run() {
        int num =0;
        while(!Thread.currentThread().isInterrupted()&&num<=Integer.MAX_VALUE /2){
            if(num%10000 == 0){
                System.out.println(num+"是10000得倍数");
            }
            num++;
        }
        System.out.println("任务结束了");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleep());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
