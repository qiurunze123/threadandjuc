package com.geek.VolatileExample;

/**
 * @author 邱润泽 bullock
 */
public class Volatile2 {

    private static volatile boolean isOver = false;

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isOver){
                    System.out.println("我是死循环！！！");
                } ;
            }
        });
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOver = true;
    }
}
