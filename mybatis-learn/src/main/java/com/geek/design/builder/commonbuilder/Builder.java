package com.geek.design.builder.commonbuilder;

/**
 * @author 邱润泽 bullock
 */
public abstract  class Builder {

    abstract void buildFrame();
    abstract void buildSeat();
    abstract void buildTire();
    abstract Bike createBike();
}
