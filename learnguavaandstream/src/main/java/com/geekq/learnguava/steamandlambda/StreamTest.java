package com.geekq.learnguava.steamandlambda;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 * <p>
 * 流式操作
 * <p>
 * 所有的流操作会被组合到一个 stream pipeline中，这点类似linux中的pipeline概念，
 * 将多个简单操作连接在一起组成一个功能强大的操作。一个 stream pileline首先会有一个数据源
 * 这个数据源可能是数组、集合、生成器函数或是IO通道，流操作过程中并不会修改源中的数据
 * 然后还有零个或多个中间操作，每个中间操作会将接收到的流转换成另一个流（比如filter）
 * 最后还有一个终止操作，会生成一个最终结果（比如sum）
 * 流是一种惰性操作，所有对源数据的计算只在终止操作被初始化的时候才会执行
 */
public class StreamTest {

    public static void main(String[] args) {

        //创建流的几种方式
        //流操作 分为三部分： 1.源 2.0个或者多个中间操作 3.终止操作（到这一步才会执行整个stream pipeline计算）

        Stream<String> stream = Stream.of("hello", "hello world", "helloworld");
        String[] array = new String[]{"hello", "hello world", "helloworld"};
        Stream<String> stringStream = Arrays.stream(array);

        Stream<String> stream2 = Arrays.asList("hello", "hello world", "helloworld").stream();

        //将流中的所有字符转换成大写返回一个新的集合
        //传递的是一种行为
        Function<String,String> function = s -> s.toUpperCase();

        List<String> newList = stream2.map(function).collect(Collectors.toList());

        Stream<List<Integer>> listStream =
                Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        List<Integer> collect1 = listStream.flatMap(theList -> theList.stream()).
                map(integer -> integer * integer).collect(Collectors.toList());

        collect1.forEach((Integer i)-> System.out.println(i));

        List<Integer> list = Arrays.asList(1,3,5,7,9);

        //获取最大最小平均值
        IntSummaryStatistics statistics = list.stream().filter((Integer i)->i>2).mapToInt(i->i*2).skip(2).summaryStatistics();

        System.out.println(statistics.getMax());

        //从1开始，每个元素比前一个元素大2，最多生成10个元素
        Stream.iterate(1,item -> item + 2).limit(10).forEach(System.out::println);

        //Stream陷阱 distinct()会一直等待产生的结果去重，将distinct()和limit(6)调换位置，先限制结果集再去重就可以了
        IntStream.iterate(0, i -> (i + 1) % 2).distinct().limit(6).forEach(System.out::println);
    }

}
