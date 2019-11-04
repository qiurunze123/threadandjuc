package com.geekq.learnguava.steamandlambda;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 */
public class ConcatSetsDemo {

    public static void main(String[] args) {
        Set<Book> set1 = new HashSet<>();
        Set<Book> set2 = new HashSet<>();
        {
            set1.add(new Book("Core Java", 200));
            set1.add(new Book("Spring MVC", 300));
            set1.add(new Book("Learning Freemarker", 150));

            set2.add(new Book("Core Java", 200));
            set2.add(new Book("Spring MVC", 300));
            set2.add(new Book("Learning Hibernate", 400));
        }
        Set<Book> set = Stream.concat(set1.stream(), set2.stream()).collect(Collectors.toSet());
        set.forEach(b->System.out.println(b.getName()+", "+ b.getPrice()));
    }
}
