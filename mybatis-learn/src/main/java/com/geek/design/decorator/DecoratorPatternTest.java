package com.geek.design.decorator;

/**
 * @author 邱润泽 bullock
 */
public class DecoratorPatternTest {

    public static void main(String[] args) {
        ChineseStyle chineseStyle = new ChineseStyle();
        House redChineseStyle = new RedDecorator(new ChineseStyle());
        House redEuropeanStyle = new RedDecorator(new EuropeanStyle());

        System.out.println("中式装修");
        chineseStyle.style();

        System.out.println("\n中式加红色墙");
        redChineseStyle.style();

        System.out.println("\n欧式加红色墙");
        redEuropeanStyle.style();
    }
}
