package com.geekq.learnguava.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ImmutableCollectionsTest
{

    @Test(expected = UnsupportedOperationException.class)
    public void testOf()
    {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertThat(list, notNullValue());
        list.add(4);
        fail();
    }

    @Test
    public void testCopy()
    {
        Integer[] array = {1, 2, 3, 4, 5};
        System.out.println(ImmutableList.copyOf(array));
    }

    @Test
    public void testBuilder()
    {
        ImmutableList<Integer> list = ImmutableList.<Integer>builder()
                .add(1)
                .add(2, 3, 4).addAll(Arrays.asList(5, 6))
                .build();
        System.out.println(list);
    }

    @Test
    public void testImmutableMap()
    {
        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder().put("Oracle", "12c")
                .put("Mysql", "7.0").build();
        System.out.println(map);
        try
        {
            map.put("Scala", "2.3.0");
            fail();
        } catch (Exception e)
        {
            assertThat(e instanceof UnsupportedOperationException, is(true));
        }
    }
}