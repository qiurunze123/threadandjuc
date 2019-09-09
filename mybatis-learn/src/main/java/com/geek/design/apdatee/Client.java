package com.geek.design.apdatee;

/**
 * @author 邱润泽 bullock
 */
public class Client {

    public static void main(String[] args) {

        //创建源对象（被适配的对象）
        Adaptee adaptee = new Adaptee();
        //利用源对象对象一个适配器对象，提供客户端调用的方法
        Adapter adapter = new Adapter(adaptee);
        System.out.println("客户端调用适配器中的方法");
        adapter.request();

    }

    //客户端调用适配器中的方法
    //适配器包装源接口对象，调用源接口的方法
    //源接口对象调用源接口中的方法
}
