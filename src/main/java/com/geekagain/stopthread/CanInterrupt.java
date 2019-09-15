package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 * 如果 while 里面 try catch 会导致中断失效 标记为会被清除
 */
public class CanInterrupt {
    public static void main(String[] args) {

        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                int num =0 ;
                while(num<=10000){
                    if(num % 100 ==0){
                        System.out.println(num+" 100 得倍数");
                    }
                    num ++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
