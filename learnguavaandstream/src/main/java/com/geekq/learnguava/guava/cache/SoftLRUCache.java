package com.geekq.learnguava.guava.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftLRUCache<K, V> implements LRUCache<K, V>
{


    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, SoftReference<V>>
    {
        final private int limit;

        public InternalLRUCache(int limit)
        {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest)
        {
            return this.size() > limit;
        }
    }

    private final int limit;

    private final InternalLRUCache<K, V> cache;

    public SoftLRUCache(int limit)
    {
        this.limit = limit;
        this.cache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value)
    {
        this.cache.put(key, new SoftReference<>(value));
    }

    @Override
    public V get(K key)
    {
        SoftReference<V> reference = this.cache.get(key);
        if (null == reference) return null;
        return reference.get();
    }

    @Override
    public void remove(K key)
    {
        this.cache.remove(key);
    }

    @Override
    public int size()
    {
        return this.cache.size();
    }

    @Override
    public void clear()
    {
        this.cache.clear();
    }

    @Override
    public int limit()
    {
        return this.limit;
    }
}
