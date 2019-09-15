package com.geekagain.volatilego;

/**
 * @author 邱润泽 bullock 演示volatile得局限性 part1 看似可行
 *
 * 陷入阻塞 时 volatile 是无法中断线程的  此例中 生产者 的生产速度很快 消费者的消费速度很慢
 * 所以阻塞队列满了以后 生产者就会阻塞 等待消费者进一步消费
 */
public class WrongWayVolatileCantStop implements Runnable {


    @Override
    public void run() {

    }
}
