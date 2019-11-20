package com.apollo.threadinterview.threadsafe;

/**
 * 描述：     初始化未完毕，就this赋值 1,1
 *
 * 在主函数 sleep 105毫秒的时候 是 打印
 *               10 的时候 就是 1.0
 */
public class ThreadError4 {

    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
        Thread.sleep(10);
//        Thread.sleep(200);
        if (point != null) {
            System.out.println(point);
        }
    }
}

class Point {

    private final int x, y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        System.out.println("================= y:"+y);
        ThreadError4.point = this;
        Thread.sleep(100);
        System.out.println("================= y:"+y);
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