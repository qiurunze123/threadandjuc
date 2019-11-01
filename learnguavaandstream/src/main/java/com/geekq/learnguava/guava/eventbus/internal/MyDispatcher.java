package com.geekq.learnguava.guava.eventbus.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class MyDispatcher
{

    private final Executor executorService;

    private final MyEventExceptionHandler exceptionHandler;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public static final Executor PRE_THREAD_EXECUTOR_SERVICE = PreThreadExecutorService.INSTANCE;

    private MyDispatcher(Executor executorService, MyEventExceptionHandler exceptionHandler)
    {
        this.executorService = executorService;
        this.exceptionHandler = exceptionHandler;
    }


    public void dispatch(Bus bus, MyRegistry registry, Object event, String topic)
    {
        ConcurrentLinkedQueue<MySubscriber> subscribers = registry.scanSubscriber(topic);
        if (null == subscribers)
        {
            if (exceptionHandler != null)
            {
                exceptionHandler.handle(new IllegalArgumentException("The topic " + topic + " not bind yet"),
                        new BaseMyEventContext(bus.getBusName(), null, event));
            }

            return;
        }

        subscribers.stream().filter(subscriber -> !subscriber.isDisable())
                .filter(subscriber ->
                {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return (aClass.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> realInvokeSubscribe(subscriber, event, bus));
    }

    private void realInvokeSubscribe(MySubscriber subscriber, Object event, Bus bus)
    {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executorService.execute(() ->
        {
            try
            {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e)
            {
                if (null != exceptionHandler)
                {
                    exceptionHandler.handle(e, new BaseMyEventContext(bus.getBusName(), subscriber, event));
                }
            }
        });
    }

    public void close()
    {
        if (executorService instanceof ExecutorService)
            ((ExecutorService) executorService).shutdown();
    }

    static MyDispatcher newDispatcher(MyEventExceptionHandler exceptionHandler, Executor executor)
    {
        return new MyDispatcher(executor, exceptionHandler);
    }

    static MyDispatcher seqDispatcher(MyEventExceptionHandler exceptionHandler)
    {
        return new MyDispatcher(SEQ_EXECUTOR_SERVICE, exceptionHandler);
    }

    static MyDispatcher perThreaDDispatcher(MyEventExceptionHandler exceptionHandler)
    {
        return new MyDispatcher(PRE_THREAD_EXECUTOR_SERVICE, exceptionHandler);
    }

    private static class SeqExecutorService implements Executor
    {

        private final static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }


    private static class PreThreadExecutorService implements Executor
    {

        private final static PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command)
        {
            new Thread(command).start();
        }
    }

    private static class BaseMyEventContext implements MyEventContext
    {

        private final String eventBusName;

        private final MySubscriber subscriber;

        private final Object event;

        private BaseMyEventContext(String eventBusName, MySubscriber subscriber, Object event)
        {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource()
        {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber()
        {
            return subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribe()
        {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent()
        {
            return this.event;
        }
    }
}
