package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邱润泽 bullock
 */
public class DistinctSimpleDemo {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("AA", "BB", "CC", "BB", "CC", "AA", "AA");
        long l = list.stream().distinct().count();
        System.out.println("No. of distinct elements:"+l);
        String output = list.stream().distinct().collect(Collectors.joining(","));
        System.out.println(output);
    }
}
