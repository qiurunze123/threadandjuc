package com.geek.design.factory.abstractfactory;

/**
 * @author 邱润泽 bullock
 *
 * 抽象工厂是生产一整套有产品的（至少要生产两个产品)
 * 这些产品必须相互是有关系或有依赖的，而工厂方法中的工厂是生产单一产品的工厂。
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {

        Factory factory;
        Gun gun;
        Bullet bullet;

        factory =new AK_Factory();
        bullet=factory.produceBullet();
        bullet.load();
        gun=factory.produceGun();
        gun.shooting();

    }
}
