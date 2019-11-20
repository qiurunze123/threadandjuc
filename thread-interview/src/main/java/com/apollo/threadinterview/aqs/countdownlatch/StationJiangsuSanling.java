package com.apollo.threadinterview.aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class StationJiangsuSanling extends DangerCenter {

	public StationJiangsuSanling(CountDownLatch countDown) {
		super(countDown, "江苏三林调度站");
	}

	@Override
	public void check() {
		System.out.println("正在检查 [" + this.getStation() + "]...");
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("检查 [" + this.getStation() + "] 完毕，可以发车~");
	}

}
