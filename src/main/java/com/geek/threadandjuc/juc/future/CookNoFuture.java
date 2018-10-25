package com.geek.threadandjuc.juc.future;

/**
 * 在厨具没有送到的时候我们在线程里面什么都做不了
 */
public class CookNoFuture {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        // 第一步 网购厨具
        OnlineShopping thread = new OnlineShopping();
        thread.start();
        thread.join();  // 保证厨具送到
        // 第二步 去超市购买食材
        Thread.sleep(2000);  // 模拟购买食材时间
        Shicai shicai = new Shicai();
        System.out.println("第二步：食材到位");
        // 第三步 用厨具烹饪食材
        System.out.println("第三步：开始展现厨艺");
        cook(thread.chuju, shicai);

        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }

    // 网购厨具线程
    static class OnlineShopping extends Thread {

        private Chuju chuju;

        @Override
        public void run() {
            System.out.println("第一步：下单");
            System.out.println("第一步：等待送货");
            try {
                Thread.sleep(5000);  // 模拟送货时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一步：快递送到");
            chuju = new Chuju();
        }

    }

    //  用厨具烹饪食材
    static void cook(Chuju chuju, Shicai shicai) {

        System.out.println("用锅煮饭！");
    }

    // 厨具类
    static class Chuju {}

    // 食材类
    static class Shicai {}

}
