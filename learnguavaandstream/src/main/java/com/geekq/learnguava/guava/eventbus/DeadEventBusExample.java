package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.listeners.DeadEventListener;
import com.google.common.eventbus.EventBus;

public class DeadEventBusExample
{

    public static void main(String[] args)
    {

        final DeadEventListener deadEventListener = new DeadEventListener();
        final EventBus eventBus = new EventBus("DeadEventBus")
        {
            @Override
            public String toString()
            {
                return "DEAD-EVENT-BUS";
            }
        };
        eventBus.register(deadEventListener);
        eventBus.post("Hello");
    }
}
