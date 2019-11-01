package com.geekq.learnguava.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ListsExampleTest
{

    @Test
    public void testCartesianProduct()
    {

        List<List<String>> result = Lists.cartesianProduct(
                Lists.newArrayList("1", "2"),
                Lists.newArrayList("A", "B")
        );
        System.out.println(result);
    }

    @Test
    public void testTransform()
    {
        ArrayList<String> sourceList = Lists.newArrayList("Scala", "Guava", "Lists");
        Lists.transform(sourceList, e -> e.toUpperCase()).forEach(System.out::println);


    }

    @Test
    public void testNewArrayListWithCapacity()
    {
        ArrayList<String> result = Lists.newArrayListWithCapacity(10);
        result.add("x");
        result.add("y");
        result.add("z");
        System.out.println(result);


    }

    //Apache NIFI
    //Hotworks HDF
    @Test
    public void testNewArrayListWithExpectedSize(){
        Lists.newArrayListWithExpectedSize(5);
    }

    @Test
    public void testReverse(){
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list),equalTo("1,2,3"));

        List<String> result = Lists.reverse(list);
        assertThat(Joiner.on(",").join(result),equalTo("3,2,1"));
    }

    @Test
    public void testPartition(){
        ArrayList<String> list = Lists.newArrayList("1", "2", "3","4");
        List<List<String>> result = Lists.partition(list, 30);
        System.out.println(result.get(0));
    }
}