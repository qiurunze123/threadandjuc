package com.geek.design.builder.commonbuilder;

/**
 * @author 邱润泽 bullock
 * 指挥者
 */
public class Director {

    private Builder mBuilder = null;
    public Director(Builder builder) {
        mBuilder = builder;
    }
    public Bike construct() {
        mBuilder.buildFrame();
        mBuilder.buildSeat();
        mBuilder.buildTire();
        return mBuilder.createBike();
    }
}
