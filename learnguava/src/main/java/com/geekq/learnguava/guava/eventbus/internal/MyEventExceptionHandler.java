package com.geekq.learnguava.guava.eventbus.internal;

public interface MyEventExceptionHandler
{
    void handle(Throwable cause, MyEventContext context);
}
