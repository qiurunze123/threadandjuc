package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 */
public class EuropeanStyle implements House {
    @Override
    public void style() {
        System.out.println("欧式装修风格");
    }
}
