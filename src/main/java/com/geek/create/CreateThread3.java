package com.geek.create;

/**
 * @author 邱润泽 bullock
 */
public class CreateThread3  implements  Runnable{

    public static void main(String[] args) {
        Thread t = new Thread(new CreateThread3());
        t.start();
    }

    @Override
    public void run() {
        System.out.println("你好吗 小菜！");
    }
}
