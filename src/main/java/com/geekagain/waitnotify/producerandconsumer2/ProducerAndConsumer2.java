package com.geekagain.waitnotify.producerandconsumer2;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author 邱润泽 bullock
 *
 * 生产者消费者模式 wait notify notifyall
 *
 *
 */
public class ProducerAndConsumer2 {

    public static void main(String[] args) {
        EventStorage eventStorage = new EventStorage();
        Producer producer = new Producer(eventStorage);
        Consumer consumer = new Consumer(eventStorage);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

    static class Producer implements Runnable{
        private EventStorage storage;

        public Producer(
                EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100 ; i++) {
                storage.put();
            }
        }
    }
    static class Consumer implements Runnable {

        private EventStorage storage;

        public Consumer(
                EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.take();
            }
        }
    }

    static class EventStorage{
        private int maxSize;
        private LinkedList<Date> storge;

        public EventStorage(){
            maxSize =10;
            storge = new LinkedList<>();
        }

        public synchronized  void put(){
            while(storge.size() == maxSize){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storge.add(new Date()) ;
            System.out.println("仓库里面已经有了"+storge.size()+"个产品");
            notify();
        }

        public synchronized void take(){
            while (storge.size() == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //poll  拿到并删除
            System.out.println("拿到了"+storge.poll()+",仓库还剩下"+storge.size());
            notify();
        }
    }

}
