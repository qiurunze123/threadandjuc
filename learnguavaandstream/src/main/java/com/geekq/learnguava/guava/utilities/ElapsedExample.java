package com.geekq.learnguava.guava.utilities;

import java.util.concurrent.TimeUnit;

public class ElapsedExample {

    public static void main(String[] args) throws InterruptedException {
        process("3463542353");
    }

    /**
     * splunk
     * @param orderNo
     * @throws InterruptedException
     */
    private static void process(String orderNo) throws InterruptedException {

        System.out.printf("start process the order %s\n", orderNo);
        long startNano = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);

        System.out.printf("The orderNo %s process successful and elapsed %d ns.\n", orderNo, (System.nanoTime() - startNano));
    }
}
