package com.geek.design.JDKProxy;

/**
 * @author 邱润泽 bullock
 */
public class XiaoJuZi implements Girl {

    @Override
    public void date() {
        System.out.println("---我是王美丽，我们约会吧---");
    }

    @Override
    public void watchMovie() {
        System.out.println("---我是王美丽，我们看电影吧---");
    }
}
