package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 */
public class StreamUseMind {

    //Predicate函数式接口的主要作用就是提供一个test方法
    // 接受一个参数返回一个布尔类型
    // Predicate在stream api中进行一些判断的时候非常常用
    public static void main(String[] args) {
        Predicate<Integer> p = num->num%2 ==0;

        List<Integer> list = Arrays.asList(3,5,3);

        //所有的元素 都与给定的 Predicate 匹配 返回true
        System.out.println("allMatch:" + list.stream().allMatch(p));
        //任意的元素 都与给定的 Predicate 匹配 返回true
        System.out.println("anyMatch:" + list.stream().anyMatch(p));
        //没有元素 都与给定的 Predicate 匹配 返回true
        System.out.println("noneMatch:" + list.stream().noneMatch(p));


        List<Integer> list1 = Arrays.asList(3,5,7);

        int  sum = list1.stream().collect(Collectors.summingInt(i->i));
        System.out.println("sum:"+sum);

        System.out.println("-----------------------------------");
        //filter 过滤符合条件的返回值
        List<Integer> list2 = Arrays.asList(3,5,7);
        System.out.println("count:"+list2.stream().filter(p).count());

        System.out.println("----------------------------------");

        List<String> list3 = Arrays.asList("G","B","F","E");
        //finyany 和 findFirst 都是返回第一个 findany
        // 最快处理完的那个线程的数据
        String any = list3.stream().findAny().get();
        System.out.println("FindAny: "+ any);
        String first = list3.stream().findFirst().get();
        System.out.println("FindFirst: "+ first);

        System.out.println("----------------------------------");

        //filter  过滤掉的 是那些 不匹配的
        Integer[][] data = {{1,7},{3,4},{5,6}};
        Arrays.stream(data).flatMap(row -> Arrays.stream(row)).
                filter(num -> num%2 == 1)
                .forEach(s -> System.out.print(s+" "));

        System.out.println("-----------------------------------");

        //flatMapToInt(): It is used with primitive data type int and returns IntStream.
        //flatMapToLong(): It is used with primitive data type long and returns LongStream.
        //flatMapToDouble(): It is used with primitive data type double and returns DoubleStream .
        List<Integer> ints =  Arrays.asList(1,2,3,4);
        ints.stream().flatMapToInt(integer -> {
            return IntStream.of(integer);
        }).forEach(s-> System.out.println(s));

        System.out.println("------------------------------------");
        String str = "Hello World!";
        //generate  返回无限个流 limit 5 控制一下数量
        Stream<String> stream = Stream.generate(str::toString).limit(5);
        stream.forEach(s->System.out.println(s));

        System.out.println("------------------------------------------");
        //peek 返回一个新的流
        List<String> list5 = Arrays.asList("A","B","C");
        list.stream().peek(s->System.out.println(s+s)).collect(Collectors.toList());

        System.out.println("---------------------------------------------");

        //reduce 使用一个开始值然后在进行操作
        int[] array = {3,5,10,15};
        int sum1 = Arrays.stream(array).reduce(9, (x,y) -> x+y);
        System.out.println("Sum:"+ sum1);

        System.out.println("------------------------------------");
        //skip 跳过
        int[] array2 = {3,5,10,15};
        Arrays.stream(array2).skip(2)
                .forEach(s -> System.out.println(s+ " "));

        //Stream.toArray() 返回一个包含流元素的数组

        List<String> list9 = Arrays.asList("A", "B", "C", "D");
        Object[] array5 = list9.stream().toArray();
        System.out.println("Length of array: "+array5.length);

    }
}
