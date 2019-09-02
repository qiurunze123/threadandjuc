package com.geek.design.factory.commonfactory;

import com.geek.design.factory.Rectangle;
import com.geek.design.factory.Shape;

/**
 * @author 邱润泽 bullock
 */
public class RectangleFactory implements Factory {

    @Override
    public Shape getShape() {
        // Auto-generated method stub
        return new Rectangle();
    }

}