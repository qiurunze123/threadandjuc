package com.geekagain.synchronizedLearn;

import org.springframework.objenesis.instantiator.sun.UnsafeFactoryInstantiator;
import sun.misc.Unsafe;

/**
 * @author 邱润泽 bullock
 */
public class SynchronizedTest {

    private Object object = new Object();

    public String getName(){

        synchronized (object){
            System.out.println("我来测试一下！！！！！");
        }
        return "";
    }


    public static void main(String[] args) {

        SynchronizedTest test = new SynchronizedTest();
        test.getName();
    }
}
