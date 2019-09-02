package com.geek.design.factory.simplefactory;

import com.geek.design.factory.Shape;

/**
 * @author 邱润泽 bullock
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        Shape shape = ShapeFactory.getShape("CIRCLE");
        shape.draw();
    }
}
