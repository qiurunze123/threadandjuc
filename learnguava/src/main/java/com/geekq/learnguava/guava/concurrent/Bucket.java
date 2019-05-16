package com.geekq.learnguava.guava.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

public class Bucket
{

    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    private final static int BUCKET_LIMIT = 1000;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor offerMonitor = new Monitor();
    private final Monitor pollMonitor = new Monitor();

    public void submit(Integer data)
    {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> container.size() < BUCKET_LIMIT)))
        {
            try
            {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ",current size:" + container.size());
            } finally
            {
                offerMonitor.leave();
            }
        } else
        {
            throw new IllegalStateException("The bucket is full.");
        }
    }


    public void takeThenConsume(Consumer<Integer> consumer)
    {
        if (pollMonitor.enterIf(pollMonitor.newGuard(() -> !container.isEmpty())))
        {
            try
            {
                System.out.println(currentThread() + " waiting " + limiter.acquire());
                consumer.accept(container.poll());
            } finally
            {
                pollMonitor.leave();
            }
        }
    }
}
