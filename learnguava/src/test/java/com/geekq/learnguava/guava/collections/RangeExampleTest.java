package com.geekq.learnguava.guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RangeExampleTest
{
    /**
     * {x|a<=x<=b}
     */
    @Test
    public void testClosedRange()
    {
        Range<Integer> closedRange = Range.closed(0, 9);
        System.out.println(closedRange);

        assertThat(closedRange.contains(5), is(true));
        assertThat(closedRange.lowerEndpoint(), equalTo(0));
        assertThat(closedRange.upperEndpoint(), equalTo(9));
    }

    /**
     * {x|a<x<b}
     */
    @Test
    public void testOpenRange()
    {
        Range<Integer> openRange = Range.open(0, 9);
        System.out.println(openRange);

        assertThat(openRange.contains(5), is(true));

        assertThat(openRange.lowerEndpoint(), equalTo(0));
        assertThat(openRange.upperEndpoint(), equalTo(9));
        assertThat(openRange.contains(0), is(false));
        assertThat(openRange.contains(9), is(false));
    }

    /**
     * {x|a<x<=b}
     */
    @Test
    public void testOpenClosedRange()
    {
        Range<Integer> openClosedRange = Range.openClosed(0, 9);
        System.out.println(openClosedRange);

        assertThat(openClosedRange.contains(5), is(true));

        assertThat(openClosedRange.lowerEndpoint(), equalTo(0));
        assertThat(openClosedRange.upperEndpoint(), equalTo(9));
        assertThat(openClosedRange.contains(0), is(false));
        assertThat(openClosedRange.contains(9), is(true));
    }


    /**
     * {x|a<=x<b}
     */
    @Test
    public void testClosedOpenRange()
    {
        Range<Integer> closedOpen = Range.closedOpen(0, 9);
        System.out.println(closedOpen);

        assertThat(closedOpen.contains(5), is(true));

        assertThat(closedOpen.lowerEndpoint(), equalTo(0));
        assertThat(closedOpen.upperEndpoint(), equalTo(9));
        assertThat(closedOpen.contains(0), is(true));
        assertThat(closedOpen.contains(9), is(false));
    }

    /**
     * {x|x>a}
     */
    @Test
    public void testGreaterThan()
    {
        Range<Integer> range = Range.greaterThan(10);
        assertThat(range.contains(10), is(false));
        assertThat(range.contains(Integer.MAX_VALUE), is(true));
    }

    @Test
    public void testMapRange()
    {
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();

        treeMap.put("Scala", 1);
        treeMap.put("Guava", 2);
        treeMap.put("Java", 3);
        treeMap.put("Kafka", 4);
        System.out.println(treeMap);

        NavigableMap<String, Integer> result = Maps.subMap(treeMap, Range.openClosed("Guava", "Java"));
        System.out.println(result);
    }

    @Test
    public void testOtherMethod()
    {
        Range<Integer> atLeastRange = Range.atLeast(2);
        System.out.println(atLeastRange);
        assertThat(atLeastRange.contains(2), is(true));
        System.out.println(Range.lessThan(10));
        System.out.println(Range.atMost(5));
        System.out.println(Range.all());
        System.out.println(Range.downTo(10, BoundType.CLOSED));
        System.out.println(Range.upTo(10, BoundType.CLOSED));
    }

    @Test
    public void testRangeMap()
    {
        RangeMap<Integer, String> gradeScale = TreeRangeMap.create();
        gradeScale.put(Range.closed(0, 60), "E");
        gradeScale.put(Range.closed(61, 70), "D");
        gradeScale.put(Range.closed(71, 80), "C");
        gradeScale.put(Range.closed(81, 90), "B");
        gradeScale.put(Range.closed(91, 100), "A");

        assertThat(gradeScale.get(77), equalTo("C"));
    }
}