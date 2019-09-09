package com.geek.design.apdatee;

/**
 * @author 邱润泽 bullock
 * 需要被适配的接口
 */
//源接口（已经存在的接口）
//需要被转换的对象
//这个接口需要重新配置以适应目标接口
public class Adaptee {

    public void specifiRequest() {
        System.out.println("源接口对象调用源接口中的方法");
    }
}
