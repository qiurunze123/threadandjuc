package com.geek.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author 邱润泽 bullock
 */
public class AtomicIntegerFieldUpdater1 {

    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdater1> update = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdater1.class, "a");
    private static AtomicIntegerFieldUpdater1 test = new AtomicIntegerFieldUpdater1();
    public volatile int a = 100;
    public static void main(String[] args){
        for(int i=0; i<100;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(update.compareAndSet(test, 100, 120)){
                        System.out.print("已修改");
                    }
                }
            });
            t.start();
        }
    }
}
