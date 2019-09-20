package com.geekagain.waitnotify.producerandconsumer;

/**
 * @author 邱润泽 bullock
 *
 * 抽象仓库类
 */
public interface AbstractStorage {

    void consume(int num);
    void produce(int num);

}
