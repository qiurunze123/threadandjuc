package com.geek.future.future2;

import java.util.concurrent.Callable;

/**
 * @author 邱润泽 bullock
 */
public class BasketBallBuyFutureJob implements Callable<BasketBallFuture> {


    @Override
    public BasketBallFuture call() throws Exception {
        BasketBallFuture basketBallFuture =new BasketBallFuture();
        System.out.println(basketBallFuture);
        System.out.println("下单");
        System.out.println("等待收揽包裹！");
        try {
            Thread.sleep(5000);
            System.out.println("快递出发！"+"basketBall 已经到达");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return basketBallFuture ;
    }


}
