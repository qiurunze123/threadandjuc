package com.geek.design.factory.commonfactory;

import com.geek.design.factory.Circle;
import com.geek.design.factory.Shape;

/**
 * @author 邱润泽 bullock
 */
public class CircleFactory implements Factory {
    @Override
    public Shape getShape() {
        return new Circle();
    }
}
