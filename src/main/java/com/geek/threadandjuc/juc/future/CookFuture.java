package com.geek.threadandjuc.juc.future;


import org.joda.time.DateTime;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//线程异步
//这个继承体系中的核心接口是Future。Future的核心思想是：一个方法f，计算过程可能非常耗时，等待f返回，显然不明智。可以在调用f的时候，
// 立马返回一个Future，可以通过Future这个数据结构去控制方法f的计算过程。
//这里的控制包括：
//get方法：获取计算结果（如果还没计算完，也是必须等待的）
//cancel方法：还没计算完，可以取消计算过程
//isDone方法：判断是否计算完
//isCancelled方法：判断计算是否被取消
//这些接口的设计很完美，FutureTask的实现注定不会简单，后面再说。
public class CookFuture {
    public static void main(String[] args) {
        System.out.println(new DateTime().withTimeAtStartOfDay().toDate());

        long startTime = System.currentTimeMillis();//计算开始时间

//        Callable<Chuju>  onlineBuyCook = new Callable<Chuju>() {
//            @Override
//            public Chuju call() throws Exception {
//                System.out.println("第一步：下单！");
//                System.out.println("第二步：等待送货！");
//                Thread.sleep(5000);//5秒送到
//                System.out.println("第三步快递到家！");
//                return new Chuju();
//            }
//        };

        FutureTask<Chuju> task = new FutureTask<Chuju>(new OnlineBuyCook());

        new Thread(task).start();

        // 第二步 去超市购买食材
        try {
            Thread.sleep(2000);  // 模拟购买食材时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Shicai shicai = new Shicai();
        System.out.println("第二步：食材到位");

        //第三步 用厨具烹饪食物
        if(!task.isDone()){
            System.out.println("厨具是否到货！ 进行下一步操作！");
        }

        Chuju chuju =null;
        try {
             chuju = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("厨具到货展现厨艺！");

        Cook(chuju,shicai);

        System.out.println("总共用时："+ (System.currentTimeMillis()-startTime)+"ms");
    }

    static void Cook(Chuju chuju,Shicai shicai){}

    //厨具
    static class Chuju{}

    //食材
    static class Shicai{}


    public static class OnlineBuyCook implements Callable<Chuju>{

        @Override
        public Chuju call() throws Exception {
            System.out.println("第一步：下单！");
            System.out.println("第二步：等待送货！");
            Thread.sleep(5000);//5秒送到
            System.out.println("第三步快递到家！");
            return new Chuju();
        }
    }
}
