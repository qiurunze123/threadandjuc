package com.geek.design.factory.simplefactory;

import com.geek.design.factory.Circle;
import com.geek.design.factory.Rectangle;
import com.geek.design.factory.Shape;
import com.geek.design.factory.Square;

/**
 * @author 邱润泽 bullock
 */
public class ShapeFactory  {

    public static Shape getShape(String typeShape){

        if (typeShape == null) {
            return null;
        }
        if (typeShape.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (typeShape.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (typeShape.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}
