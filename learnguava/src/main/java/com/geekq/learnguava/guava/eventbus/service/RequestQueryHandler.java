package com.geekq.learnguava.guava.eventbus.service;

import com.geekq.learnguava.guava.eventbus.events.Request;
import com.geekq.learnguava.guava.eventbus.events.Response;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestQueryHandler
{

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void doQuery(Request request)
    {
        LOGGER.info("start query the orderNo [{}]", request.toString());
        Response response = new Response();
        this.eventBus.post(response);
    }
}
