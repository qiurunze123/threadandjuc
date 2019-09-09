package com.geek.design.builder.abstractbuilder;

import com.geek.design.builder.commonbuilder.Bike;

/**
 * @author 邱润泽 bullock
 */
public class ClientTest {

    public static void main(String[] args) {
        Director director = new Director(new MobikeBuilder());
        Bike bike = director.construct();
        System.out.println(bike.getFrame());
        System.out.println(bike.getSeat());
        System.out.println(bike.getTire());

    }
}
