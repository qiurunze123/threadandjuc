package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 *
 * 懒汉式 线程不安全
 */
public class Singleton3 {

    private static Singleton3 instance ;

    private Singleton3(){

    }

    public static Singleton3 getInstance(){
        //这里刚进来 就又来了一个请求
        if(instance == null){
            instance = new Singleton3();
        }
        return instance ;
    }
}
