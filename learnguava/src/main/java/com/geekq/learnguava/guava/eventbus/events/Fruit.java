package com.geekq.learnguava.guava.eventbus.events;

import com.google.common.base.MoreObjects;
public class Fruit
{

    private final String name;

    public Fruit(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("Name", name).toString();
    }
}