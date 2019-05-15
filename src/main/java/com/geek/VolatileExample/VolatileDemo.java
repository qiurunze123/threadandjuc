package com.geek.VolatileExample;

import java.util.concurrent.TimeUnit;

/**
 * @author 邱润泽 bullock
 *
 * 验证   volatile 的 可见性
 */
class MyData {
    volatile int number = 0;

    public void addTO60() {
        this.number = 60;
    }
}

/**
 * 1.验证volatile的可见性 *1.1假如int number =0 ；number变量之前没有添加volatile修饰，没有可见性,加过之后，及时通知了main线程
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in ");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTO60();
            System.out.println(Thread.currentThread().getName() + "\t update number value " + myData.number);
        }, "AAA").start();
        while (myData.number == 0) {
        }
        System.out.println(Thread.currentThread().getName() + "\t main is over");
    }
}