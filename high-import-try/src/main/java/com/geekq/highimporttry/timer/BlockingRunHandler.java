package com.geekq.highimporttry.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 邱润泽 bullock
 */
public class BlockingRunHandler implements Runnable {


    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BlockingRunHandler.class);
    private LinkedBlockingQueue<Long> queue;
    public BlockingRunHandler(LinkedBlockingQueue<Long> queue ) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Long orderId = null;
        try {
            while ((orderId = queue.poll()) != null) {
                logger.info("================= order:{}", orderId);
            }

            logger.info("========= end ======== order:{}", orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
