package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.listeners.SimpleListener;
import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncEventBusExample
{
    public static void main(String[] args)
    {
        AsyncEventBus eventBus = new AsyncEventBus(new SeqExecutor());
        eventBus.register(new SimpleListener());
        eventBus.post("hello");

    }

    static class SeqExecutor implements Executor
    {

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }
}
