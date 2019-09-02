package com.geek.design.factory;

/**
 * @author 邱润泽 bullock
 * 圆形
 */
public class Circle implements Shape {
    public Circle() {
        System.out.println("Circle");
    }
    @Override
    public void draw() {
        System.out.println("Draw Circle");
    }
}