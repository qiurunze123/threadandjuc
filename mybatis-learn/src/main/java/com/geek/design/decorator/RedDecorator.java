package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 * 具体装饰类
 */
public class RedDecorator extends HouseDecorator {


    public RedDecorator(House house) {
        super(house);
    }

    @Override
    public void style() {
        this.house.style();
        System.out.println("红色装饰墙");
    }
}