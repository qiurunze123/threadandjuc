package com.geek.VolatileExample;

/**
 * @author 邱润泽 bullock
 */
public class VolatileExample  {

    private int a = 0;
    private volatile boolean flag = false;
    public void writer(){
        //1
        a = 1;
        //2
        flag = true;
    }
    public void reader(){
        //3
        if(flag){
            //4
            int i = a;
            System.out.println("我能感知到！");
        }
    }

    public static void main(String[] args) {
        final VolatileExample a = new VolatileExample();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                a.writer();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                a.reader();
            }
        });
        t1.start();
        t2.start();
    }


}
