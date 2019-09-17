package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 */
public class RigihtWayDealMethodException2 implements Runnable {
    @Override
    public void run() {
        while(true ){
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("被终结");
                break;
            }
            try {
                throwExceptionInMethod();
            } catch (InterruptedException e) {
                System.out.println("停止程序");
                    Thread.currentThread().interrupt();
            }
        }
    }

    //不要在你的底层代码里捕获InterruptedException异常后不处理，会处理不当
    private void throwExceptionInMethod() throws InterruptedException {
            Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
       Thread thread =  new Thread(new RigihtWayDealMethodException2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
