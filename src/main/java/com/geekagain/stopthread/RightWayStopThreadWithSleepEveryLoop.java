package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock 如果在执行过程中每次循环都会调用slepp和wait方法 则不需要每次迭代都进行检查
 */
public class RightWayStopThreadWithSleepEveryLoop {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int num = 0;
                try {
//                     && !Thread.currentThread().isInterrupted() 如果是循环则不需要
                    while (num <= 10000) {
                        if (num % 100 == 0) {
                            System.out.println(num+" we is  100 multiple");
                        }
                        num++;
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();

    }
}
