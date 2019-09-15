package com.geekagain.volatilego;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 邱润泽 bullock 演示volatile得局限性 part1 看似可行
 * <p>
 * 陷入阻塞 时 volatile 是无法中断线程的  此例中 生产者 的生产速度很快 消费者的消费速度很慢
 * 所以阻塞队列满了以后 生产者就会阻塞 等待消费者进一步消费
 */
public class WrongWayVolatileCantStop {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);
        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        Thread.sleep(1000);
        Consumer consumer = new Consumer(storage);

        while(consumer.needMoreNums()){
            System.out.println(consumer.storage.take() +" === 被消费了");
            Thread.sleep(100);
        }

        System.out.println("消费者不需要更多数据了 ---- ");
        //一旦消费者不需要更多数据 我们应该让生产者也停下里
        producer.canceled =true;
    }
}

//生产者
class Producer implements Runnable {

    public volatile boolean canceled = false;
    BlockingQueue storage;

    public Producer(BlockingQueue storage) {
        this.storage = storage;
    }

    @Override
    public void run() {

        int num = 0;
        try {
            while (num <= 10000&&!canceled) {
                if (num % 100 == 0) {
                    System.out.println(num + " 是100得倍数  放入仓库");
                    // 在put 满的时候 就会一直阻塞在这
                    storage.put(num);
                }
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行 stop");
        }
    }

}

class Consumer {
    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        if (Math.random() > 0.95) {
            return false;
        }
        return true;
    }

}