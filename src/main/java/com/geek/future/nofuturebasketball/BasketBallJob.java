package com.geek.future.nofuturebasketball;

/**
 * @author 邱润泽 bullock
 *
 * 模拟不用future 打球  必须等待 在篮球没有送到之前 我们不可以干别的事情 多线程失去了意思
 *
 * run 核心方法run是没有返回值的；如果要保存run方法里面的计算结果，必须等待run方法计算完，无论计算过程多么耗时
 *
 */
public class BasketBallJob {

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis() ;
        BasketBallBuyJob basketBallBuyJob = new BasketBallBuyJob();
        Thread bask = new Thread(basketBallBuyJob);
        bask.start();
        bask.join();  // 保证篮球包裹到达  必须要有求你才能开始
        Thread.sleep(2000);  // 寻找场地
        BaskBallSite baskBallSite =new BaskBallSite("洛克公园");
        System.out.println("寻找场地! 场地就绪"+baskBallSite.getName());

        Thread.sleep(2000);
        System.out.println("展现球季开始练习我nba的身手！");
        GoPlay(basketBallBuyJob.getBasketBall(),baskBallSite);
        long end = System.currentTimeMillis() ;
        System.out.println("时间花费 === "+  (end-start));

    }

    static void GoPlay(BasketBall basketBall , BaskBallSite baskBallSite) {

        System.out.println(basketBall+"==== start!   开玩  ====== " +baskBallSite.getName());
    }


}
