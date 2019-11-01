package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.listeners.SimpleListener;
import com.google.common.eventbus.EventBus;

public class SimpleEventBusExample
{
    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        eventBus.post("Simple Event");
    }
}
