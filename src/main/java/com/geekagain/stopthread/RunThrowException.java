package com.geekagain.stopthread;

/**
 * @author 邱润泽 bullock
 * run 无法抛出checked Exception 只能用try catch
 */
public class RunThrowException {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //来自于顶层方法 顶层方法对于异常没有任何处理
            }
        });
    }
}
