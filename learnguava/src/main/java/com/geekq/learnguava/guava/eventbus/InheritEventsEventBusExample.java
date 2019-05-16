package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.events.Apple;
import com.geekq.learnguava.guava.eventbus.events.Fruit;
import com.geekq.learnguava.guava.eventbus.listeners.FruitEaterListener;
import com.google.common.eventbus.EventBus;
public class InheritEventsEventBusExample
{
    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FruitEaterListener());
        eventBus.post(new Apple("apple"));
        System.out.println("============================");
        eventBus.post(new Fruit("apple"));
    }
}
