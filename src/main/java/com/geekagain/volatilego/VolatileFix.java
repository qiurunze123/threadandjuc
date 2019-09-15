package com.geekagain.volatilego;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 邱润泽 bullock
 */
public class VolatileFix {

    public static void main(String[] args) throws InterruptedException {
        VolatileFix fix =new VolatileFix();
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);
        Producer producer = fix.new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        Thread.sleep(1000);
        Consumer consumer = fix.new Consumer(storage);
        while(consumer.needMoreNums()){
            System.out.println(consumer.storage.take() +" === 被消费了");
            Thread.sleep(100);
        }

        System.out.println("消费者不需要更多数据了 ---- ");

        //一旦消费者不需要更多数据 我们应该让生产者也停下里
//        producer.canceled =true;

        producerThread.interrupt();
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
                    while (num <= 10000 &&!Thread.currentThread().isInterrupted()) {
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
}
