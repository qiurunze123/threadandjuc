package com.geekq.learnguava.guava.eventbus.internal;

import java.util.concurrent.Executor;
public class MyEventBus implements Bus
{

    private final MyRegistry registry = new MyRegistry();

    private String busName;

    private final static String DEFAULT_BUS_NAME = "default";

    private final static String DEFAULT_TOPIC = "default-topic";

//    private final MyEventExceptionHandler exceptionHandler;

    private final MyDispatcher dispatcher;

    public MyEventBus()
    {
        this(DEFAULT_BUS_NAME, null, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(String busName)
    {
        this(busName, null, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    MyEventBus(String busName, MyEventExceptionHandler exceptionHandler, Executor executor)
    {
        this.busName = busName;
        this.dispatcher = MyDispatcher.newDispatcher(exceptionHandler, executor);
    }

    public MyEventBus(MyEventExceptionHandler exceptionHandler)
    {
        this(DEFAULT_BUS_NAME, exceptionHandler, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    @Override
    public void register(Object subscriber)
    {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber)
    {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event)
    {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic)
    {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close()
    {
        this.dispatcher.close();
    }

    @Override
    public String getBusName()
    {
        return this.busName;
    }
}
