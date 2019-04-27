package com.geek.consumerandproduct;

class Consumer implements Runnable {
    private String name;
    private Storage s = null;

    public Consumer(String name, Storage s) {
        this.name = name;
        this.s = s;
    }

    public void run() {
        try {
            while (true) {
                System.out.println(name + "准备消费产品.");
                /**
                 * 取 生产的产品
                 */
                Product product = s.pop();
                System.out.println(name + "已消费(" + product.toString() + ").");
                System.out.println("===============");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
