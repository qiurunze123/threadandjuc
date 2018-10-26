/**   
 * 
 * @Title: RealResult.java 
 * @Prject: DemoThread
 * @Package: com.liang.demo.high.futrue 
 * @author: yin.hl
 * @date: 2017年3月26日 下午9:09:52 
 * @version: V1.0   
 */
package com.geek.threadandjuc.juc.future.futuretype;

/**
 * 真实数据（用户真实想得到的数据从这里获取)
 *
 */
public class RealData implements Data {

	private String result;

	@Override
	public String getRealData() {
		return result;
	}

	//根据参数初始化数据
	public RealData initRealData(String param) {
		System.out.println("根据" + param + "进行查询，这是一个很耗时的操作..");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("操作完毕，获取结果");
		result = "查询结果";
		return this;
	}

}
