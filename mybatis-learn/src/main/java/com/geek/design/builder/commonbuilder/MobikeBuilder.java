package com.geek.design.builder.commonbuilder;

/**
 * @author 邱润泽 bullock
 */
public class MobikeBuilder extends Builder {

    private Bike mBike = new Bike();
    @Override
    void buildFrame() {
        mBike.setFrame("mobike真皮");
    }
    @Override
    void buildSeat() {
        mBike.setSeat("mobike椅子");
    }
    @Override
    void buildTire() {
        mBike.setTire("mobike轮胎");
    }
    @Override
    Bike createBike() {
        return mBike;
    }
}
