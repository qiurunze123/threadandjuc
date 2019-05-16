package com.geekq.learnguava.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcreteListener extends BaseListener
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ConcreteListener.class);

    @Subscribe
    public void conTask(String event)
    {
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("The event [{}] will be handle by {}.{}", event, this.getClass().getSimpleName(), "conTask");
        }
    }
}
