package com.geek.design.factory.abstractfactory;

/**
 * @author 邱润泽 bullock
 */
public class AK_Bullet implements Bullet {
    @Override
    public void load() {
        System.out.println("Load bullets with AK");
    }
}
