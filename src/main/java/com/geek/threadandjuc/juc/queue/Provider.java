package com.geek.threadandjuc.juc.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 * 产生数据放入队列
 */
public class Provider implements Runnable{
	
	//生产者的名字
	private String name;
	
	//消息队列,用于存放生产者产生的数据
	private BlockingQueue<Data> queue;
	
	//多线程间是否启动变量，有强制从主内存中刷新的功能。即时返回线程的状态
	private volatile boolean isRunning = true;
	
	//数据id生成器
	private static AtomicInteger count = new AtomicInteger();
	
	//随机对象
	private static Random random = new Random(); 
	
	public Provider(String name,BlockingQueue queue){
		this.name = name;
		this.queue = queue;
	}

	@Override
	public void run() {
		while(isRunning){
			try {
				//随机休眠0 - 1000 毫秒 表示创建数据的耗时
				Thread.sleep(random.nextInt(1000));
				//生成数据的ID
				int id = count.incrementAndGet();
				
				//创建数据对象
				Data data = new Data(Integer.toString(id), "数据" + id);
				
				if(!this.queue.offer(data, 2, TimeUnit.SECONDS)){
					System.out.println("生产者"+name+"将"+data.getName()+"放入队列中超时...");
				}else{
					System.out.println("生产者"+name+ ", 创建了数据-id为:" + id + ",并放入消息队列中");	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop(){
		this.isRunning = false;
	}
	
}
