package com.geek.design.builder.abstractbuilder;

import com.geek.design.builder.commonbuilder.Bike;
import com.geek.design.builder.commonbuilder.Builder;

/**
 * @author 邱润泽 bullock
 */
public class MobikeBuilder extends NewBuilder {

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
