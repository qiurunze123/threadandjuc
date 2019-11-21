package com.apollo.threadinterview.threadjmm;

/**
 * @author 邱润泽 bullock
 *
 * a=3   b=2 ------ 交替运行 先运行a=3 然后交替运行 b 还没有修改 就变成了2 --------
 * a=1   b=2 T2 先执行
 * a=3   b=3 T1 先执行
 *
 * b=3 a=1  线程2 只能看到 b的修改 看不完整 因为主内存没有同步过来
 *
 *
 *
 *
 * 两个线程的通信是有一定的延迟和代价的 主从同步需要付出一定时间代价
 *
 * 可能出现 另一个线程看到一半的情况 比如只看到了b 没有看到a 的修改
 */
public class ThreadJmm3 {
   int a = 1;
   int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    private void print()
    {
        if(b==3&&a==1){
            System.out.println("+++++++++++++"+"b="+b+";a="+a+"+++++++++++++++++");
        }
        System.out.println("b="+b+";a="+a);
    }

    public static void main(String[] args) {
        while (true) {
            ThreadJmm3 test = new ThreadJmm3();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //T1 线程调用change更改线程
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //T2 线程调用print打印线程
                    test.print();
                }
            }).start();
        }

    }
}
