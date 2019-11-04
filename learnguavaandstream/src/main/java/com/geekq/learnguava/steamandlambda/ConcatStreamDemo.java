package com.geekq.learnguava.steamandlambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 */
public class ConcatStreamDemo {

    public static void main(String[] args) {

        //concat 把俩个stream串联起来
        Stream<String> s1 = Stream.of("AA","BB","CC");
        Stream<String> s2 = Stream.of("CC","DD","FF");
        Stream<String> s = Stream.concat(s1,s2);
        s.forEach(e-> System.out.println(e));

        System.out.println("------------------------------");
        s1 = Stream.of("AA","BB","CC");
        s2 = Stream.of("CC","DD","FF");
        //distinct 我来去个重
        s = Stream.concat(s1,s2).distinct();
        s.forEach(e-> System.out.println(e));

        System.out.println("---------------------------");

        //将俩个集合转换成流 操作

        List<Book> list1 = new ArrayList<>();
        List<Book> list2 = new ArrayList<>();

        list1.add(new Book("Core Java", 200));
        list1.add(new Book("Spring MVC", 300));
        list1.add(new Book("Learning Freemarker", 150));

        list2.add(new Book("Core Java", 200));
        list2.add(new Book("Spring MVC", 300));
        list2.add(new Book("Learning Hibernate", 400));

        List<Book> list = Stream.concat(list1.stream(),list2.stream()).collect(Collectors.toList());

        list.forEach((Book b)-> System.out.println(b.getName()+"===="+b.getPrice()));
    }
}
