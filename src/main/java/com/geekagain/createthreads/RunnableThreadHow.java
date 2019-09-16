package com.geekagain.createthreads;

/**
 * @author 邱润泽 bullock
 */
public class RunnableThreadHow {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i come from runnbale ");
            }

        })
        //在重写这块得run方法实际上已经覆盖掉了 父类得run方法 继承thread被重写  即便target传入也没有用
        {
            @Override
            public void run() {
                System.out.println(" i cmme from thread ");
            }
        }.start();

    }
}
