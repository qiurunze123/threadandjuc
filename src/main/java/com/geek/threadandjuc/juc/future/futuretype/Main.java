package com.geek.threadandjuc.juc.future.futuretype;

/**
 * 
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		//1.客户端发送请求,此处的客户端就是Main方法
		FutureController fc = new FutureController();
		//2.服务端的Controller.handler方法被调用,并传入了参数
		//3.此处返回的Data对象为FutureData对象,返回的是Data的代理类
		FutureData data = fc.handler("UserId");
		System.out.println("请求发送成功...");
		System.out.println("继续其他的处理...");
		
		//3.获取真实数据
		System.out.println("Main开始获取真实结果");
		String result = data.getRealData();
		System.out.println(result);
	}
}
