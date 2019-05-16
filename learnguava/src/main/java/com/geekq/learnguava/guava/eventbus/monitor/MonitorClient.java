package com.geekq.learnguava.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * tail
 * Apache Flume 1.7 Spooling
 * <p>
 * .position
 */
public class MonitorClient
{
    public static void main(String[] args) throws Exception
    {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "G:\\Teaching\\汪文君Google Guava实战视频\\monitor");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() ->
        {
            try
            {
                monitor.stopMonitor();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);
        executorService.shutdown();
        monitor.startMonitor();

    }
}

