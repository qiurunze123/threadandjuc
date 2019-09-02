package com.geek.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 邱润泽 bullock
 */
public class XiaoJuZiProxy implements InvocationHandler {

    private Girl girl;

    public XiaoJuZiProxy(Girl girl) {
        super();
        this.girl = girl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSomeThingBefore();
        Object ret =  method.invoke(girl,args);
        doSomeThingEnd();
        return ret;
    }

    private void doSomeThingBefore(){
        System.out.println("小举的父母说 ： 想和小举约会就需要我们来调查一下你");
    }

    private void doSomeThingEnd(){
        System.out.println("小举的父母说 ： 闺女 帅邱有没有对你动手动脚");
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(),  girl.getClass().getInterfaces(),this);
    }
}
