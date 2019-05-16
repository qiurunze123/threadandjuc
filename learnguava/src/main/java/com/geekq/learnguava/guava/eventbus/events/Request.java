package com.geekq.learnguava.guava.eventbus.events;

public class Request
{

    private final String orderNo;

    public Request(String orderNo)
    {
        this.orderNo = orderNo;
    }

    @Override
    public String toString()
    {
        return "Request{" +
                "orderNo='" + orderNo + '\'' +
                '}';
    }
}
