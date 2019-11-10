package com.geekq.learnguava.steamandlambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @auther 邱润泽 bullock
 * @date 2019/11/3
 *
 * 匿名内部类在swing中写法很常见 但是在java7之前是不可以的
 * 在java8中可以用lambda表达式 很好的写出来
 *
 * lambda表达式的基本结构(param1,param2,param3)->{}
 */
public class SwingTest {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("My JFrame");
        JButton jButton = new JButton("my Button");

//        jButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button Pressed!");
//            }
//        });

        jButton.addActionListener( (ActionEvent e )-> {
                System.out.println("Button Pressed!");
        });

        jFrame.add(jButton);

        jFrame.pack();

        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
