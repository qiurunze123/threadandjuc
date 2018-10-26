/**   
 * 
 * @Title: FutureDemo.java 
 * @Prject: DemoThread
 * @Package: com.liang.demo.high.futrue 
 * @author: yin.hl
 * @date: 2017年3月26日 下午9:12:26 
 * @version: V1.0   
 */
package com.geek.threadandjuc.juc.future.futuretype;


/** 
 * 接收请求的控制器(例如接收PC端请求)
 * 
 */
public class FutureController {

	public FutureData handler(final String param){
		//由于真实的数据还没有准备好,所以返回一个真实Data数据的代理类
		final FutureData futureData = new FutureData();
		
		//2 启动一个新的线程，去加载真实的数据，传递给这个代理对象
		new Thread(new Runnable() {
			@Override
			public void run() {
				futureData.initRealData(param);
			}
		}).start();
		
		return futureData;
	}
}
