package com.geek.consumerandproduct;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumer {

    public static void main(String[] args) {
        Storage s = new Storage();
        ExecutorService service = Executors.newCachedThreadPool();
        Producer p = new Producer("张三", s);
        Producer p2 = new Producer("李四", s);
        Consumer c = new Consumer("王五", s);
        Consumer c2 = new Consumer("老刘", s);
        Consumer c3 = new Consumer("老林", s);
        service.submit(p);
        service.submit(c);
        service.submit(c2);
        service.submit(c3);

    }
}
