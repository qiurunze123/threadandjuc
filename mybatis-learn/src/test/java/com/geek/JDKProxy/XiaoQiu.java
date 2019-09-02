package com.geek.JDKProxy;

/**
 * @author 邱润泽 bullock
 */
public class XiaoQiu {
    public XiaoQiu() {
    }

    public static void main(String[] args) {
        //隔壁有个女孩叫小举
        Girl girl =new XiaoJuZi();
        //想要和她约会必须征通他家里人同意
        XiaoJuZiProxy family = new XiaoJuZiProxy(girl);
        //征得妈妈同意
        // 为什么我们这里可以将其转化为Girl类型的对象？原因就是在newProxyInstance这个方法的第二个参数上
        // ，我们给这个代理对象提供了一组什么接口，那么我这个代理对象就会实现了这组接口，
        // 这个时候我们当然可以将这个代理对象强制类型转化为这组接口中的任意一个，
        // 因为这里的接口是Subject类型，所以就可以将其转化为Subject类型了
        Girl monther = (Girl) family.getProxyInstance();
        //通过妈妈这个代理才能与小举约会
        monther.date();
        System.out.println(family.getClass());
        System.out.println(monther.getClass());
    }
}
