package com.geek.CASDemo;

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

        System.out.println(atomicInteger.compareAndSet(5,2019));
        System.out.println(atomicInteger.compareAndSet(5,2020));

        System.out.println(atomicInteger.get());

    }
}
