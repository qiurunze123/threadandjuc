package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 */
public class RigihtWayDealMethodException implements Runnable {
    @Override
    public void run() {
        while(true ){
            System.out.println("lets go");
            try {
                throwExceptionInMethod();
            } catch (InterruptedException e) {
                System.out.println("停止程序");
                break;
            }
        }
    }

    //不要在你的底层代码里捕获InterruptedException异常后不处理，会处理不当
    private void throwExceptionInMethod() throws InterruptedException {
            Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
       Thread thread =  new Thread(new RigihtWayDealMethodException());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
