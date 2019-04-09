package com.geek.stop;

/**
 * @author 邱润泽 bullock
 */
public class YieldWhat {

    volatile static int i=0;
    volatile static int j=0;


    public static void main(String[] args) {

        Thread t1=new Thread(){
            @Override
            public void run(){
                while(true){
                    i++;
                    Thread.yield();
                }
            }
        };
        Thread t2=new Thread(){
            @Override
            public void run(){
                while(true){
                    j++;
                    Thread.yield();
                }
            }
        };
        t1.start();
        t2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("i="+i);
        System.out.println("j="+j);
        t1.stop();
        t2.stop();
    }
}
