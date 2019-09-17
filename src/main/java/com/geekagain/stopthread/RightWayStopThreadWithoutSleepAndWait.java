package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 *
 * 没有 sleep 或者wait 方法如何停止线程
 */
public class RightWayStopThreadWithoutSleepAndWait implements Runnable{
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            System.out.println("在没有sleep等方法结束了线程!");
        }
        System.out.println("任务结束了");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleepAndWait());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
