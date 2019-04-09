package com.geek.create;

/**
 * @author 邱润泽 bullock
 */
public class CreateThread2 {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                System.out.println("你好吗！ 小菜");
            }
        } ;

        t.start();
    }
}
