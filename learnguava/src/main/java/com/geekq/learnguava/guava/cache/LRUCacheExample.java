package com.geekq.learnguava.guava.cache;

import java.util.concurrent.TimeUnit;

public class LRUCacheExample
{

    public static void main(String[] args) throws InterruptedException
    {
        final SoftLRUCache<String, byte[]> cache = new SoftLRUCache<>(100);

        for (int i = 0; i < 1000; i++)
        {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached.");
        }
    }
}
