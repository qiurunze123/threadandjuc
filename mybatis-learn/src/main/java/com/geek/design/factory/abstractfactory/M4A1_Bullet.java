package com.geek.design.factory.abstractfactory;

/**
 * @author 邱润泽 bullock
 */
public class M4A1_Bullet implements Bullet {
    @Override
    public void load() {
        System.out.println("Load bullets with M4A1");
    }
}
