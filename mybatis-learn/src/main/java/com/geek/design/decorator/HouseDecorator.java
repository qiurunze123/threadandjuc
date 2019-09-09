package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 * 抽象装饰器
 */
public class HouseDecorator implements House {

    public  House house;
    public HouseDecorator(House house) {
        this.house = house;
    }

    @Override
    public void style() {
        house.style();
    }
}
