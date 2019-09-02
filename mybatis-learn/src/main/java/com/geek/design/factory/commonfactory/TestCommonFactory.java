package com.geek.design.factory.commonfactory;

import com.geek.design.factory.Shape;
import com.geek.design.factory.commonfactory.CircleFactory;
import com.geek.design.factory.commonfactory.Factory;

/**
 * @author 邱润泽 bullock
 */
public class TestCommonFactory  {

    public static void main(String[] args) {
        Factory factory = new CircleFactory();
        Shape shape = factory.getShape();
        shape.draw();
    }
}
