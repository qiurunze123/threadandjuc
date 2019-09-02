package com.geek.design.factory.abstractfactory;

/**
 * @author 邱润泽 bullock
 */
public class M4A1_Factory implements Factory {
    @Override
    public Gun produceGun() {
        return new M4a1();
    }

    @Override
    public Bullet produceBullet() {
        return new M4A1_Bullet();
    }
}
