package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 * 具体装饰类
 */
public class BlueDecorator extends HouseDecorator {
    public BlueDecorator(House house) {
        super(house);
    }

    @Override
    public void style() {
       this.house.style();
        System.out.println("刷蓝墙");
    }
}
