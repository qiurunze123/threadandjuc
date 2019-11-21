package com.apollo.threadinterview.threadjmm;

import java.util.concurrent.CountDownLatch;

/**
 * @author qiurunze
 * @date 2019/11/16
 * 描述：     演示重排序的现象 “直到达到某个条件才停止”，测试小概率事件
 *
 *  * 1 a=1;x=b(0) ; b=1;y=a(1) 最终结果是x=0 y=1
 *  * 2 b=1;y=a(0) ; a=1;x=b(1) 最终结果是x=1 y=0 将线程1和2 调换下顺序会有大概率发生
 *  ******** 需要保持同时执行才可以 所以 countdowmlatch  来保持线程同步 *********
 *  * 3 b=1;a=1;   x=b(1) ;y=a(1) 最终结果是x=1 y=1
 *
 *  这三个结果都是默认得是 执行得顺序不会变才会发生得 我们交替的无非是 线程a和线程b顺序
 *  但是线程得内部是否可以变换顺序呢????
 *  ==========================================================================
 *
 *
 * 执行代码顺序 结果发生了(0,0) 因为重排序发生了
 * 执行顺序如下:
 *
 * y=a a=1 x=b b=1
 *
 * 最终输出xy结果 （0,0）得情况
 *
 */
public class ThreadJmm2 {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            //保持第二次进入的时候又是原始状态
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            CountDownLatch latch = new CountDownLatch(3);

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            });
            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            });
            two.start();
            one.start();
            latch.countDown();
            one.join();
            two.join();

            String result = "第" + i + "次（" + x + "," + y + ")";
//            if (x == 1 && y == 1) {
//                System.out.println("============="+result+"==============");
//                break;
//            }
                if (x == 0 && y == 0) {
                System.out.println("============="+result+"==============");
                break;
            } else {
                System.out.println(result);
            }
        }
    }


}
