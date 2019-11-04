package com.geekq.learnguava.steamandlambda;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 */
public class Test6 {


    public static void main(String[] args) {
        List<String> listStream = Arrays.asList("pony", "tony", "jack");

        listStream.stream().map((String s) -> {
            return s.toUpperCase();
        }).forEach(System.out::println);


        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3, 1)
        );

        //flatMap 将最底层元素抽出来放到一起，最终 output 的新 Stream 里面
        Stream<Integer> outputStream = inputStream.flatMap((List childList) -> childList.stream());
        outputStream.forEach(System.out::println);
        System.out.println("------------------------------------------------");
        Stream<List<Integer>> inputStreamDistinct = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3, 1)
        );

        inputStreamDistinct.distinct().forEach(System.out::println);

        System.out.println("-------------------------------------------------");

        //map 方法用于映射每个元素到对应的结果 filter 方法用于通过设置的条件过滤出元素
        List<String> strings = Arrays.asList("james", "nongmei", "");
        strings.stream().filter((String s) -> {
            return !s.isEmpty();
        }).forEach(System.out::println);

        System.out.println("--------------------------------------------------");

        //limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素。以下代码片段使用 limit 方法保理4个元素
        List<Integer> numbers = Arrays.asList(3,2,2,3,7,3,5);

        numbers.stream().limit(4).forEach(System.out::println);

        System.out.println("---------------------------------------------------");
        //skip 扔掉前n个元素
        numbers.stream().skip(4).forEach(System.out::println);
        System.out.println("----------------------------------------------------");
        Stream<List<String>> stream = Stream.of(Arrays.asList("geekq","holy","bench"))  ;
        stream.forEach((List<String> s )->{
            for (String s1:s) {
                System.out.println(s1);
            }
        });
        System.out.println("----------------------------------------------------");

        //sorts 对流进行排序
        List<Integer> integerList = Arrays.asList(1,2,6,8,7);
        integerList.stream().sorted().forEach(System.out::println);

        System.out.println("----------------------------------------------------");

        //查找字符串长度为6

        List<String> stringList = Arrays.asList("cat","dog","pig","dock","horse");

        Stream s = stringList.stream().filter(string -> string.length()<= 6).map(String::length).sorted().limit(3)
                .distinct();

        s.forEach(System.out::println);

        System.out.println("-------------------------------------------------------");

        //stream.of  和 .stream区别  感觉是 第二个 不给你一个包含集合元素的流；相反，它将给您一个具有单个元素的流，它是集合本身(而不是它的元素)
        // 如果需要包含集合元素的流，则必须使用entities.stream().
        //stream 最终操作 foreach
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        System.out.println("--------------------------------------------------------");
        //count 统计流中元素个数
        List<String> list = Arrays.asList("Hollis", "HollisChuang", "hollis","Hollis666", "Hello", "HelloWorld", "Hollis");
        System.out.println(list.stream().count());

        System.out.println("---------------------------------------------------------");

        //collect 是一个规约操作 将流中的操作归集成一个结果集

        List<String> listCollect = Arrays.asList("Hollis", "HollisChuang", "hollis","Hollis666", "Hello", "HelloWorld", "Hollis");

        listCollect = listCollect.stream().filter((String s1)->s1.startsWith("Hollis")).collect(Collectors.toList());
        System.out.println(listCollect);

        List<String> list1 = new ArrayList<>();
        list1.add("Hollis");

        System.out.println(list1);

        //Stream 创建 中间操作 最终操作
        //创建方式有俩种 集合类的stream方法 通过stream的of方法
        //Stream的中间操作可以用来处理Stream，中间操作的输入和输出都是Stream，中间操作可以是过滤、转换、排序等。
        //
        //Stream的最终操作可以将Stream转成其他形式，如计算出流中元素的个数、将流装换成集合、以及元素的遍历等。
        //



    }
}
