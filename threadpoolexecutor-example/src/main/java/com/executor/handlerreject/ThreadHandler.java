package com.executor.handlerreject;

import com.executor.GeekQThreadPoolExecutor;

/**
 * @author 邱润泽 bullock
 */
public interface ThreadHandler {

    void rejected(Runnable runnable , GeekQThreadPoolExecutor geekQThreadPoolExecutor);
}
