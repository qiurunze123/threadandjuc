package com.geekq.learnguava.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CacheLoaderTest4
{

    @Test
    public void testCacheStat()
    {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5).recordStats().build(loader);
        assertCache(cache);
    }

    @Test
    public void testCacheSpec()
    {
        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec builderSpec = CacheBuilderSpec.parse(spec);
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(builderSpec).build(loader);

        assertCache(cache);
    }

    private void assertCache(LoadingCache<String, String> cache)
    {
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));//ALEX
        CacheStats stats = cache.stats();
        System.out.println(stats.hashCode());
        assertThat(stats.hitCount(), equalTo(0L));
        assertThat(stats.missCount(), equalTo(1L));

        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));

        stats = cache.stats();
        System.out.println(stats.hashCode());
        assertThat(stats.hitCount(), equalTo(1L));
        assertThat(stats.missCount(), equalTo(1L));

        System.out.println(stats.missRate());
        System.out.println(stats.hitRate());
    }
}
