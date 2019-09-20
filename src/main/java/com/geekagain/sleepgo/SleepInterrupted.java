package com.geekagain.sleepgo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 邱润泽 bullock
 * 每个一秒钟输出当前时间 被中断 观察
 * timeUnit.Seconds.sleep()
 */
public class SleepInterrupted implements Runnable{


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new SleepInterrupted());
        thread.start();
        Thread.sleep(6500);
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10 ; i++) {
            System.out.println(new Date());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.out.println("被中断了 菜逼");
                e.printStackTrace();
            }
        }
    }
}
