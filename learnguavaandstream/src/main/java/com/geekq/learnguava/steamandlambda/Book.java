package com.geekq.learnguava.steamandlambda;

/**
 * @author 邱润泽 bullock
 */
public class Book {

    private String name ;

    private int price ;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Book setPrice(int price) {
        this.price = price;
        return this;
    }
}
