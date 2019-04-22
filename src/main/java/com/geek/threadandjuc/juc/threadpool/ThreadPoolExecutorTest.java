package com.geek.threadandjuc.juc.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 */
public class ThreadPoolExecutorTest {
	
	public static void main(String[] args) {
		
		//AbortPolicy			抛出异常,不影响其他线程运行
		//CallerRunsPolicy		调用当前任务
		//DiscardOldestPolicy	丢弃最老的任务
		//DiscardPolicy			直接丢弃,什么也不做
		//ThreadPoolExecutor1 executor = new ThreadPoolExecutor1(3,3,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1),new AbortPolicy());
//		ThreadPoolExecutor1 executor = new ThreadPoolExecutor1(3,3,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1),new CallerRunsPolicy());
//		ThreadPoolExecutor1 executor = new ThreadPoolExecutor1(3,3,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1),new DiscardOldestPolicy());
//		ThreadPoolExecutor1 executor = new ThreadPoolExecutor1(3,3,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1),new DiscardPolicy());
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3,3,0,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(1),new MyPolicy());
		
		//ThreadPoolExecutor1 executor = new ThreadPoolExecutor1(7,7,60L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
		for(int i=0;i<8;i++){
			MyTask task = new MyTask(i);
			executor.submit(task);
		}
		
		executor.shutdown();
	}
}

class MyPolicy implements RejectedExecutionHandler{
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r+"被拒绝执行"+System.currentTimeMillis());
	}
}

class MyTask implements Runnable{

	int index = 0;
	
	public MyTask(int index) {
		this.index = index;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+">>run "+index);
	}
	
}
