package com.geek.threadandjuc.juc.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 */
public class ScheduledThreadPoolTest {

	public static void test1() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

		for (int i = 0; i < 10; i++) {
			final int index = i;
			Runnable task = new Runnable() {
				public void run() {
					System.out.println(Thread.currentThread().getName() + ">> delay " + index + " seconds run....");
				}
			};

			ScheduledFuture<?> schedule = scheduledThreadPool.schedule(task, i, TimeUnit.SECONDS);
		}

		scheduledThreadPool.shutdown();
	}

	public static void test2() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(12);
		Runnable task = new Runnable() {
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + ">> sleep..." + System.currentTimeMillis());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ">> run....." + System.currentTimeMillis());
			}
		};
		ScheduledFuture<?> scheduleAtFixedRate = scheduledThreadPool.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
		scheduleAtFixedRate.cancel(false);
	}

	public static void test3() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
		Runnable task = new Runnable() {
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + ">> sleep..." + System.currentTimeMillis());
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + ">> run....." + System.currentTimeMillis());
			}
		};
		ScheduledFuture<?> scheduleWithFixedDelay = scheduledThreadPool.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduleWithFixedDelay.cancel(true);
		scheduledThreadPool.shutdown();
	}

	public static void main(String[] args) {
		// test1();
		// test2();
		 test3();

	}
}
