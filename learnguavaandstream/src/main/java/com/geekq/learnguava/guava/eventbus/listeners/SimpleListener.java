package com.geekq.learnguava.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SimpleListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @Subscribe
    public void doAction(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action", event);
        }
    }

    @Subscribe
    public void doAction1(final String event)
    {

        try
        {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action1", event);
        }
    }

    @Subscribe
    public void doAction2(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }

    @Subscribe
    public void doAction3(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }

    @Subscribe
    public void doAction4(final String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("Received event [{}] and will take a action2", event);
        }
    }
}
