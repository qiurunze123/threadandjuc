package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 */
public class RigihtWayStopThreadInProd implements Runnable {
    @Override
    public void run() {
        while(true ){
            System.out.println("lets go");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
                System.out.println("保存日志 停止程序");
                e.printStackTrace();
            }
        }
    }

    private void throwInMethod() throws InterruptedException {
            Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
       Thread thread =  new Thread(new RigihtWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
