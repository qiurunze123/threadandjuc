package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 * 具体组件
 */
public class ChineseStyle implements House {
    @Override
    public void style() {
        System.out.println("中式装修风格");
    }
}
