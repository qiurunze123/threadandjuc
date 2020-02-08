package com.executor;

/**
 * @author 邱润泽 bullock
 */
public interface GeekQExecutorService  {

    //执行方法
    public void execute(Runnable task);

    //关闭方法
    public void shutdown();

    //获取线程池数量
    public int getCorePoolSize();

    //获取任务
    public Runnable getTask();
}
