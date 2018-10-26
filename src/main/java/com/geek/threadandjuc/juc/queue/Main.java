package com.geek.threadandjuc.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
/**
*/
public class Main {

	public static void main(String[] args) throws Exception {
		
		//消息队列
		BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>(10);

		// 生产者
		Provider p1 = new Provider("p1",queue);
		Provider p2 = new Provider("p2",queue);
		Provider p3 = new Provider("p3",queue);
		
		// 消费者
		Consumer c1 = new Consumer("c1",queue);
		Consumer c2 = new Consumer("c2",queue);
		Consumer c3 = new Consumer("c3",queue);
		Consumer c4 = new Consumer("c4",queue);
		Consumer c5 = new Consumer("c5",queue);
		Consumer c6 = new Consumer("c6",queue);
		Consumer c7 = new Consumer("c7",queue);

		// 创建线程池运行
		ExecutorService cachePool = Executors.newFixedThreadPool(8);
		cachePool.execute(p1);
		cachePool.execute(p2);
		cachePool.execute(p3);
		cachePool.execute(c1);
		cachePool.execute(c2);
		cachePool.execute(c3);
		cachePool.execute(c4);
		cachePool.execute(c5);
		cachePool.execute(c6);
		cachePool.execute(c7);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//生产者停止产生数据
		p1.stop();
		p2.stop();
		p3.stop();

		cachePool.shutdown();
	}
}
