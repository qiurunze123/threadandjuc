package com.apollo.threadinterview.aqs.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CheckStartUp {

	private static List<DangerCenter> stationList;
	private static CountDownLatch countDown;
	
	public CheckStartUp() {
	}
	
	public static boolean checkAllStations() throws Exception {

		// 初始化3个调度站
		countDown = new CountDownLatch(3);
		
		// 把所有站点添加进list
		stationList = new ArrayList<DangerCenter>();
		stationList.add(new StationBeijingIMooc(countDown));
		stationList.add(new StationJiangsuSanling(countDown));
		stationList.add(new StationShandongChangchuan(countDown));
		
		// 使用线程池
		Executor executor = Executors.newFixedThreadPool(stationList.size());
		
		for (DangerCenter center : stationList) {
			executor.execute(center);
		}
		
		// 等待线程执行完毕
		countDown.await();
		
		for (DangerCenter center : stationList) {
			if (!center.isOk()) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		boolean result = CheckStartUp.checkAllStations();
		System.out.println("监控中心针对所有危化品调度站点的检查结果为：" + result);
	}

}
