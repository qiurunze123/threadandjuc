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
public class ProducerAndConsumer3 {


    private static Object object = "3";


    public static void main(String[] args) {
        EnevtStorage eventStorage = new EnevtStorage();

        Thread t1 = new Thread(new pruducer(eventStorage));
        Thread t2 = new Thread(new consumer(eventStorage));
        t1.start();
        t2.start();
    }
    static  class pruducer implements Runnable{

        private EnevtStorage enevtStorage;

        public pruducer(EnevtStorage enevtStorage) {
            this.enevtStorage = enevtStorage;
        }

        @Override
        public void run() {
            for (int i = 0; i <100 ; i++) {
                enevtStorage.put();
            }
        }
    }


    static  class consumer implements Runnable{

        private EnevtStorage enevtStorage;

        public consumer(EnevtStorage enevtStorage) {
            this.enevtStorage = enevtStorage;
        }

        @Override
        public void run() {
            for (int i = 0; i <100 ; i++) {
                enevtStorage.take();
            }
        }
    }

static class EnevtStorage {

    private int maxSize ;
    private LinkedList<Date> storage ;

    public EnevtStorage() {
        this.maxSize = 10;
        this.storage = new LinkedList<>();
    }

    public synchronized void put (){

        if(storage.size() == maxSize){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.println("我已经进行生产  仓库里面一共有 数量： "+storage.size());
        notify();
    }

    public synchronized  void take(){
        if(storage.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //拿到并删除
        storage.poll();
        System.out.println("我已经进行消费 仓库里面一共有 数量： "+storage.size());
        notify();
    }
}

}
