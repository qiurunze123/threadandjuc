package com.geek.threadandjuc.juc.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 */
public class Consumer implements Runnable {

	// 消费者的名字
	private String name;

	// 消息队列引用
	private BlockingQueue<Data> queue;

	public Consumer(String name, BlockingQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	// 随机对象
	private static Random r = new Random();

	@Override
	public void run() {
		while (true) {
			try {
				// 获取数据
				Data data = this.queue.poll(5, TimeUnit.SECONDS);
				if (data == null) {
					System.out.println("当前消费者：" + name + ",超过5S无法获取数据,退出监听..");
					break;
				}
				// 休眠0 - 1000毫秒模拟耗时
				Thread.sleep(r.nextInt(1000));
				System.out.println("当前消费者：" + name + "， 消费成功，消费数据为id: " + data.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
