package com.geekq.learnguava.steamandlambda;

import java.util.stream.Stream;

/**
 * @author 邱润泽 bullock
 */
public class ConcatArraysDemo {

    public static void main(String[] args) {

        Book[] bk1 = new Book[3];

        Book[] bk2 = new Book[3];

        {
            bk1[0] = new Book("Core Java", 200);
            bk1[1] = new Book("Spring MVC", 300);
            bk1[2] = new Book("Learning Freemarker", 150);
            bk2[0] = new Book("Core Java", 200);
            bk2[1] = new Book("Spring MVC", 300);
            bk2[2] = new Book("Learning Hibernate", 400);
        }

        Book[] bks = Stream.concat(Stream.of(bk1),Stream.of(bk2)).toArray(b->new Book[b]);

        for (Book b:bks) {
            System.out.println(b.getName()+"==="+b.getPrice());
        }
    }
}
