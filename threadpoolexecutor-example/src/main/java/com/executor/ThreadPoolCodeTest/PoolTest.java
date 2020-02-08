package com.executor.ThreadPoolCodeTest;

import com.executor.GeekQThreadPoolExecutor;
import com.executor.handlerreject.ThreadHandlerReject;

/**
 * @author 邱润泽 bullock
 */
public class PoolTest {

    public static void main(String[] args) throws InterruptedException {
        GeekQThreadPoolExecutor pool
                = new GeekQThreadPoolExecutor(2,3,0,new ThreadHandlerReject());

        for (int i = 1; i <10 ; i++) {
            pool.execute(new Task(i));
        }

        Thread.sleep(1000);

        pool.shutdown();
    }
}
