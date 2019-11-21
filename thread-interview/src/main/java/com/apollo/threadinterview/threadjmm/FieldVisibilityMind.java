package com.apollo.threadinterview.threadjmm;

/**
 * 描述：     演示可见性带来的问题
 */
public class FieldVisibilityMind {

     int a = 1;
     int c =90;
     int d= 100;
    volatile int b = 2;

    private void change() {
        a = 3;
        c=1000;
        d=200;
        //触发器
        b=0;
    }


    private void print() {
        if(b==0){
            System.out.println("b=" + b + ";a=" + a+";c="+c+"d:=="+d);
        }
        System.out.println("b=" + b + ";a=" + a);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibilityMind test = new FieldVisibilityMind();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }

    }


}
