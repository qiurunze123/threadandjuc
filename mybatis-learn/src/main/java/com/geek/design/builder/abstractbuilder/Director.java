package com.geek.design.builder.abstractbuilder;

import com.geek.design.builder.commonbuilder.Bike;
import com.geek.design.builder.commonbuilder.Builder;

/**
 * @author 邱润泽 bullock
 */
public class Director {

    private NewBuilder mBuilder = null;
    public Director(NewBuilder builder) {
        mBuilder = builder;
    }

    public Bike construct() {
        return mBuilder.construct();
    }
}
