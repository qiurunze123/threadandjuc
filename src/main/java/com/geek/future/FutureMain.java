package com.geek.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //构造FutureTask
        FutureTask<Integer> future = new FutureTask<Integer>(new RealData(0));
        FutureTask<Integer> future1 = new FutureTask<Integer>(new RealData(0));

        ExecutorService executor = Executors.newFixedThreadPool(5);
        //执行FutureTask，相当于上例中的 client.request("a") 发送请求
        //在这里开启线程进行RealData的call()执行
        executor.submit(future);
        executor.submit(future1);

        System.out.println("请求数据 ========  start!");
        try {
            //这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        //相当于上例中得data.getContent()，取得call()方法的返回值
        //如果此时call()方法没有执行完成，则依然会等待
        System.out.println("数据 = " + future.get());
        System.out.println("数据 = " + future1.get());
        executor.shutdown();
    }
}
