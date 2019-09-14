package com.geekagain.createthreads;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 邱润泽 bullock
 */
public class DemoTimerTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },100,100);
    }
}
