package com.executor.handlerreject;

import com.executor.GeekQThreadPoolExecutor;
import com.executor.exception.ThreadException;

/**
 * @author 邱润泽 bullock
 *
 * 拒绝策略直接抛出异常 ThreadHandlerReject
 */
public class ThreadHandlerReject implements ThreadHandler {

    public ThreadHandlerReject() {
    }

    @Override
    public void rejected(Runnable runnable,
                         GeekQThreadPoolExecutor geekQThreadPoolExecutor) {
        System.out.println("任务已经满了");
        throw new ThreadException("任务已经满了");
    }
}
