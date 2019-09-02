package com.geek.design.factory;

/**
 * @author 邱润泽 bullock
 * 正方形
 */
public class Square implements Shape {
    public Square() {
        System.out.println("Square");
    }

    @Override
    public void draw() {
        System.out.println("Draw Square");
    }
}
