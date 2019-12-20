package com.apollo.threadinterview.threadpool;

/**
 * @author 邱润泽 bullock
 */
public class ThreadTest implements Runnable {
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(300);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
