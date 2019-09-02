package com.geek.design.factory.commonfactory;

import com.geek.design.factory.Shape;
import com.geek.design.factory.Square;
import com.geek.design.factory.commonfactory.Factory;

/**
 * @author 邱润泽 bullock
 */
public class SquareFactory implements Factory {

    @Override
    public Shape getShape() {
        // Auto-generated method stub
        return new Square();
    }

}