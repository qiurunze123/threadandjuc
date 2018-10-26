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
 * 饿汉模式-单例对象构建方法
 * (可以解决内存开销的问题,但是不当的写法可能引起线程安全问题)
 */
class Singleton2 {
	private static Singleton2 singleton = null; // 不建立对象
	private Singleton2() {
	}
	/*synchronized 可以解决线程安全问题,但是存在性能问题,即使singleton!=null也需要先获得锁*/
	public synchronized static Singleton2 getInstance() {
		if (singleton == null) { // 先判断是否为空
			try {
				Thread.sleep(1000);
				System.out.println("构建这个对象可能耗时很长...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			singleton = new Singleton2(); // 懒汉式做法
		}
		return singleton;
	}
	
	@Override
	public String toString() {
		return ""+this.hashCode();
	}
}
public class DemoThread23 {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService es = Executors.newFixedThreadPool(100);

		for (int i = 0; i < 100; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Singleton2.getInstance());
				}
			});
		}

		es.shutdown();
	}
}
