package com.geekagain.singletongo;

/**
 * @author 邱润泽 bullock
 *
 * 懒汉式 线程安全 不推荐使用
 */
public class Singleton4 {

    private static Singleton4 instance ;

    private Singleton4(){

    }

    public synchronized static Singleton4 getInstance(){
        //这里刚进来 就又来了一个请求 
        if(instance == null){
            instance = new Singleton4();
        }
        return instance ;
    }
}
