package com.geekq.learnguava.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

public class RateLimiterExample
{

    private final static RateLimiter limiter = RateLimiter.create(0.5d);

    private final static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args)
    {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i ->
                service.submit(RateLimiterExample::testSemaphore)
        );
    }

    private static void testLimiter()
    {
        System.out.println(currentThread() + " waiting " + limiter.acquire());
    }

    private static void testSemaphore()
    {

        try
        {
            semaphore.acquire();
            System.out.println(currentThread() + " is coming and do work.");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } finally
        {
            semaphore.release();
            System.out.println(currentThread() + " release the semaphore.");
        }
    }
}
