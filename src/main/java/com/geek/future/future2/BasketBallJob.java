package com.geek.future.future2;

import com.geek.future.nofuturebasketball.BaskBallSite;
import com.geek.future.nofuturebasketball.BasketBall;
import com.geek.future.nofuturebasketball.BasketBallBuyJob;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author 邱润泽 bullock
 *
 *
 */
public class BasketBallJob {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long start = System.currentTimeMillis() ;
        FutureTask<BasketBallFuture> futureFutureTask = new FutureTask<>(new BasketBallBuyFutureJob());

        new Thread(futureFutureTask).start();
        Thread.sleep(2000);  // 寻找场地
        BaskBallSite baskBallSite =new BaskBallSite("洛克公园");
        System.out.println("寻找场地! 场地就绪"+baskBallSite.getName());
        /**
         * 判断是否送到的判断
         */
        if(!futureFutureTask.isDone()){
            System.out.println("我还没送到 ， 谁敢造次 ！");
        }
        GoPlay(futureFutureTask.get(),baskBallSite);
        System.out.println("展现球季开始练习我nba的身手！");
        long end = System.currentTimeMillis() ;
        System.out.println("时间花费 === "+  (end-start));

    }

    static void GoPlay(BasketBallFuture basketBall , BaskBallSite baskBallSite) {

        System.out.println(basketBall+"==== start!   开玩  ====== " +baskBallSite.getName());
    }


}
