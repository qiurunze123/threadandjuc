package com.geekq.learnguava.guava.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SetsExampleTest
{

    @Test
    public void testCreate()
    {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        assertThat(set.size(), equalTo(3));

        ArrayList<Integer> list = Lists.newArrayList(1, 1, 2, 3);
        assertThat(list.size(), equalTo(4));

        HashSet<Integer> set2 = Sets.newHashSet(list);
        assertThat(set2.size(), equalTo(3));


    }

    @Test
    public void testCartesianProduct()
    {

        Set<List<Integer>> set = Sets.cartesianProduct(Sets.newHashSet(1, 2), Sets.newHashSet(3, 4), Sets.newHashSet(5, 6));

        System.out.println(set);


    }

    @Test
    public void testCombinations()
    {
        HashSet<Integer> set = Sets.newHashSet(1, 2, 3);
        Set<Set<Integer>> combinations = Sets.combinations(set, 2);
        combinations.forEach(System.out::println);
    }

    @Test
    public void testDiff()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.SetView<Integer> diffResult1 = Sets.difference(set1, set2);
        System.out.println(diffResult1);
        Sets.SetView<Integer> diffResult2 = Sets.difference(set2, set1);
        System.out.println(diffResult2);


    }

    @Test
    public void testIntersection()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.intersection(set1, set2).forEach(System.out::println);
    }

    @Test
    public void testUnionSection()
    {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 4, 6);
        Sets.union(set1, set2).forEach(System.out::println);
    }
}
