package com.geek.design.factory;

/**
 * @author 邱润泽 bullock
 * 长方形
 */
public class Rectangle implements Shape {
    public Rectangle() {
        System.out.println("Rectangle");
    }
    @Override
    public void draw() {
        System.out.println("Draw Rectangle");
    }
}