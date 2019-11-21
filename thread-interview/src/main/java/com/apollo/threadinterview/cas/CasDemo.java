package com.apollo.threadinterview.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 邱润泽 bullock
 *
 * 什么是CAS ?? compare and swap  比较和设值
 *
 * 比较并交换
 */
public class CasDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

        //快照的值是5 期望值是5 比较并交换 修改为2019
        System.out.println("结论= "+atomicInteger.compareAndSet(5,2019));
        //比较并交换
        System.out.println("结论= "+atomicInteger.compareAndSet(5,2020));

        System.out.println("结论= "+atomicInteger.getAndIncrement());


        System.out.println("------"+atomicInteger.get()+"---------");

    }
}
