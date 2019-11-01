package com.geekq.learnguava.guava.utilities;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
public class StopWatchExample {
    private final static Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);

    public static void main(String[] args) throws InterruptedException {
        process("3463542353");
    }

    /**
     * drools
     * @param orderNo
     * @throws InterruptedException
     */
    private static void process(String orderNo) throws InterruptedException {

        LOGGER.info("start process the order [{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(100);

        LOGGER.info("The orderNo [{}] process successful and elapsed [{}] min.", orderNo, stopwatch.stop().elapsed(TimeUnit.MINUTES));

        /**
         *
         */
    }
}
