package com.geekq.learnguava.guava.eventbus;

import com.geekq.learnguava.guava.eventbus.service.QueryService;
import com.geekq.learnguava.guava.eventbus.service.RequestQueryHandler;
import com.google.common.eventbus.EventBus;

public class ComEachOtherEventBusExample
{

    public static void main(String[] args)
    {
        final EventBus eventBus = new EventBus();
        QueryService queryService = new QueryService(eventBus);
        eventBus.register(new RequestQueryHandler(eventBus));
        queryService.query("werwersdf");
    }
}
