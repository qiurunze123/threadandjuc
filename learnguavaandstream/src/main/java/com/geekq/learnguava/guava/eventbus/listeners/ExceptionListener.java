package com.geekq.learnguava.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionListener
{

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionListener.class);

    @Subscribe
    public void m1(String event)
    {
        LOGGER.info("============m1======={}", event);
    }


    @Subscribe
    public void m2(String event)
    {
        LOGGER.info("============m2======={}", event);
    }


    @Subscribe
    public void m3(String event)
    {
        LOGGER.info("============m3======={}", event);
        throw new RuntimeException();
    }
}
