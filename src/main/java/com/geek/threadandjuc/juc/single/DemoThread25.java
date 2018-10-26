/**   
 * 
 * @Title: DemoThread09.java 
 * @Prject: DemoThread
 * @Package: com.liang.demo 
 * @author: yin.hl
 * @date: 2017年3月20日 下午10:55:51 
 * @version: V1.0   
 */
package com.geek.threadandjuc.juc.single;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 静态内部类-单例对象构建方法
 * (
 * 利用JDK的特性：类级内部类只有在第一次被使用的时候才被会装载，
 * 这样可以保证单例对象只有在第一次被使用的时候初始化一次，
 * 并且不需要加锁，性能得到大大提高，并且保证了线程安全。
 * )
 */
class Singleton4 {
	private static class InnerSingletion {
		private static Singleton4 single = new Singleton4();
	}
	
	private Singleton4(){
		try {
			Thread.sleep(1000);
			System.out.println("构建这个对象可能耗时很长...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Singleton4 getInstance(){
		return InnerSingletion.single;
	}
	
	@Override
	public String toString() {
		return ""+this.hashCode();
	}
}

public class DemoThread25 {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 10; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Singleton4.getInstance());
				}
			});
		}

		es.shutdown();
	}
}
