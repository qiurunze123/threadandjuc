package com.geek.VolatileExample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock
 */
class MyData2 {
    volatile int number = 0;

    //此时number是加了volatile修饰的
    public void addPlusPlus()
    {
        number++;
    }
    AtomicInteger atomicInteger =   new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }

}

/**
 * 1.验证volatile不保证原子性
 * <p>
 * 1.1原子性指的是什么意思？
 * 不可分割，完整性，即某个线程正在做某个业务时，中间不可被加塞或者分割，需要整理完整
 * 要么同时成功，要么同时失败
 * <p>
 * 运行结果表明，volatile没有保证原子性，出现丢失写值的情况（值覆盖）
 * atomicInteger 解决原子性问题
 */
public class VolatileDemo2 {
    public static void main(String[] args) {
        MyData2 myData = new MyData2();
        for (int i = 1; i <= 3000; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value :" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicInteger value :" + myData.atomicInteger);
    }
}