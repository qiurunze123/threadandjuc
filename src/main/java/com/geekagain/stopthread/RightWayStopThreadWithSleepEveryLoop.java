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
                    /*
                     * 不管循环里是否调用过线程阻塞的方法如sleep、joingo、wait，这里还是需要加上
                     * !Thread.currentThread().isInterrupted()条件，虽然抛出异常后退出了循环，显
                     * 得用阻塞的情况下是多余的，但如果调用了阻塞方法但没有阻塞时，这样会更安全、更及时。
                     */

                    while (num <= 10000 &&!Thread.currentThread().isInterrupted()) {
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
