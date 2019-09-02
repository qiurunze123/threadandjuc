package com.geek.design.factory.abstractfactory;

/**
 * @author 邱润泽 bullock
 */
public class AK_Factory implements Factory {
    @Override
    public Gun produceGun() {
        return new AK();
    }

    @Override
    public Bullet produceBullet() {
        return new AK_Bullet();
    }
}
