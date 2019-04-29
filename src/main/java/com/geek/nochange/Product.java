package com.geek.nochange;

/**
 * @author 邱润泽 bullock
 */
public final class Product {
    /**
     *     final可以确保不会被继承
     *     private确保不可被访问，final确保不可被修改
     */
    private final String a;
    private final String b;
    private final double c;

    public Product(String a, String b, double c) {
        /**
         *  创建对象时必须指定值，因为创建后将无法修改
         */
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public double getC() {
        return c;
    }
}
