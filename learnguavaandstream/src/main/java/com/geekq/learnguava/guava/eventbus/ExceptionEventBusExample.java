package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.listeners.ExceptionListener;
import com.google.common.eventbus.EventBus;

public class ExceptionEventBusExample
{
    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus((exception, context) ->
        {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        });
        eventBus.register(new ExceptionListener());

        eventBus.post("exception post");
    }

/*
    static class ExceptionHandler implements SubscriberExceptionHandler
    {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context)
        {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }
    }*/

}
