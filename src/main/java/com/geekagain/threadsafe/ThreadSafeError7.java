package com.geekagain.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 邱润泽 bullock 逸出问题
 *
 * 可以利用副本解决逸出 不直接吧对象提供给外部 利用一个副本直接代替
 */
public class ThreadSafeError7 {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
//        Thread.sleep(10);
        Thread.sleep(105);
        if (point != null) {
            System.out.println(point);
        }
    }
}

class Point {

    private final int x, y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        ThreadSafeError7.point = this;
        Thread.sleep(100);
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}

class PointMaker extends Thread {

    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
