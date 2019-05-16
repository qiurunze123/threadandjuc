package com.geekq.learnguava.guava.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class DeadEventListener
{
    @Subscribe
    public void handle(DeadEvent event)
    {
        System.out.println(event.getSource());
        System.out.println(event.getEvent());
    }
}
