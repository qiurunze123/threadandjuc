package com.geekq.learnguava.guava.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MapsExampleTest
{

    @Test
    public void testCreate()
    {
        ArrayList<String> valueList = Lists.newArrayList("1", "2", "3");
        ImmutableMap<String, String> map = Maps.uniqueIndex(valueList, v -> v + "_key");
        System.out.println(map);
        Map<String, String> map2 = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map2);
    }

    @Test
    public void testTransform()
    {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        Map<String, String> newMap = Maps.transformValues(map, v -> v + "_transform");
        System.out.println(newMap);
        assertThat(newMap.containsValue("1_value_transform"), is(true));
    }

    @Test
    public void testFilter()
    {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        Map<String, String> newMap = Maps.filterKeys(map, k -> Lists.newArrayList("1", "2").contains(k));
        assertThat(newMap.containsKey("3"), is(false));
    }
}