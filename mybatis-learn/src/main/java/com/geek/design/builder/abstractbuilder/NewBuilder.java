package com.geek.design.builder.abstractbuilder;

import com.geek.design.builder.commonbuilder.Bike;

/**
 * @author 邱润泽 bullock
 */
public abstract class NewBuilder {

    abstract void buildFrame();
    abstract void buildSeat();
    abstract void buildTire();
    abstract Bike createBike();
    /**
     * 把导演类中的construct()方法合并到抽象建造者类中
     *
     * @return 具体产品对象
     */
    public Bike construct() {
        this.buildFrame();
        this.buildSeat();
        this.buildTire();
        return this.createBike();
    }
}
