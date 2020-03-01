package com.geekq.highimporttry.timer;

import com.geekq.highimporttry.entity.Order;
import com.geekq.highimporttry.mapper.OrderMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author 邱润泽 bullock
 */
@Service
public class BlockingRunTask {




    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(BlockingRunTask.class);

    @Autowired
    private OrderMapper orderMapper;


    public void dealOrderTask(){

        List<Order> orderList = orderMapper.getOrders();

        if(CollectionUtils.isEmpty(orderList)){
            logger.info("orderList size :{}",orderList.size());
            return;
        }
        //定义队列

        LinkedBlockingQueue<Long> blockingQueue =  new LinkedBlockingQueue<>();
        for (Order order: orderList) {
                blockingQueue.add(order.getOrderId());
        }

        if (blockingQueue == null || blockingQueue.size() <= 0) {
            logger.info("info:{}","dealOrderTask taskQueue is null") ;
            return ;
        }

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 4,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());
        executorService.submit(new BlockingRunHandler(blockingQueue));
        executorService.shutdown();
        try {
            while (!executorService.isTerminated()) {
                if (executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.info("processTransferOrderTask exited successfully");
                } else {
                    logger.info("Waiting for processTransferOrderTask' quit");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            executorService.shutdownNow();
        }
    }
}
