/**   
 * 
 * @Title: FutureData.java 
 * @Prject: DemoThread
 * @Package: com.liang.demo.high.futrue 
 * @author: yin.hl
 * @date: 2017年3月26日 下午9:13:30 
 * @version: V1.0   
 */
package com.geek.threadandjuc.juc.future.futuretype;

/**
 * 真实数据的代理类, 里面包含获取真实数据的方法。 
 * 当用户请求时,就把先把代理对象返回给客户端,提高响应速度。
 * 当用户需要使用真实数据的时候,再通过getRealData方法返回真实的数据
 * 
 */
public class FutureData implements Data {

	// 真实数据的引用
	private RealData realData;
	// 标记数据是否准备好了
	private boolean isReady = false;

	@Override
	public synchronized String getRealData() {
		// 如果数据没准备好,则阻塞
		if (!isReady) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 返回真实数据,需要使用RealData的方法进行获取
		return realData.getRealData();
	}

	/**
	 *
	 * @author yin.hl
	 * @Title: initRealData
	 * @param param
	 * @return: void
	 */
	public synchronized void initRealData(String param) {
		// 如果数据已经准备完毕,就直接返回,释放对象锁
		if (isReady) {
			return;
		}
		// 如果数据没有准备完毕则使用参数初始化
		realData = new RealData().initRealData(param);
		isReady = true;
		// 进行通知
		notify();
	}

}
