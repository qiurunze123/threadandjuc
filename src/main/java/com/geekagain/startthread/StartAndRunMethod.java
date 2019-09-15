package com.geekagain.startthread;

/**
 * @author 邱润泽 bullock 对比start和run两种启动线程得方式
 */
public class StartAndRunMethod {

    public static void main(String[] args) {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        //就是一个普通方法 就像是执行一个普通方法 就是在main方法
        runable.run();

        //启动新线程检查线程状态
        //加入线程组
        // 调用start0() native  方法 是 c++ 实现的 不能直接看到源码
        new Thread(runable).start();
    }
}
