package com.geek.design.builder.commonbuilder;

/**
 * @author 邱润泽 bullock
 */
public class OfoBuilder extends Builder {

    private Bike mBike = new Bike();
    @Override
    void buildFrame() {
        mBike.setFrame("ofo真皮");
    }
    @Override
    void buildSeat() {
        mBike.setSeat("ofo椅子");
    }
    @Override
    void buildTire() {
        mBike.setTire("ofo轮胎");
    }
    @Override
    Bike createBike() {
        return mBike;
    }
}
