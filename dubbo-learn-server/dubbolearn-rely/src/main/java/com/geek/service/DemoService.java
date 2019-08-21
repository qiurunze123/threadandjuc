package com.geek.service;

/**
 * @author 邱润泽 bullock
 */
public interface DemoService {

    String sayHello(String name);

    /**
     * 测试异步方法1
     * @param name
     * @param count
     * @return
     */
    String testSync1(String name , Integer count);

    /**
     * 测试异步方法2
     * @param name
     * @param count
     * @return
     */
    String testSync2(String name , Integer count);
}
