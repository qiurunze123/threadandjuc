package com.geekq.highimporttry.timer;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.service.HighImportDataService;
import com.geekq.highimporttry.util.Constant;
import com.geekq.highimporttry.util.DateUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 邱润泽 bullock
 */
@Service
public class TimerRunner2 {

    public static final Logger logger = LoggerFactory.getLogger(TimerRunner2.class);
    @Autowired
    private HighImportDataService highImportDataService;

    @Autowired
    private ImportDataTaskDao importDataTaskDao;

    @Autowired
    private ImportDataStepDao importDataStepDao;

    private static final int IMPORT_TASK_AND_STEP_RETRY_TIME = 5;


    /**
     * ThreadFactoryBuilder是一个Builder设计模式的应用,可以设置守护进程、错误处理器、线程名字
     * <p>
     * guava
     */
    private ExecutorService executorService = new ThreadPoolExecutor(2, 2,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactoryBuilder().setNameFormat("高可用改造专用线程池-%d").build());

    /**
     * 跑任务
     */
    public void timeGo() {


        logger.info("billNewRunnerImport start 导入step  and task 数据开始 2 ！");
        long start = System.currentTimeMillis();
        Date date = new Date();
        String day = DateUtil.format(date, DateUtil.DATE_FORMAT_DAY_SHORT);
        /**
         * ======= 如果 今天的数据 存在 则无需重复导入 重试 5次 ====
         */
        boolean flagImport = false;
        int importTaskAndStepTime = 0;
        while (importTaskAndStepTime < IMPORT_TASK_AND_STEP_RETRY_TIME && !flagImport) {
            boolean todayIsTryImport = importDataTaskDao.queryTodayTaskIsImport(day) > 0;
            if (todayIsTryImport) {
                logger.info("今日{}数据已导入importdataTask 和 importdataStep 表 2", day);
            } else {
                highImportDataService.recordHandle(day);
            }
            flagImport = todayIsTryImport;
            importTaskAndStepTime++;
        }
        if (flagImport) {
            boolean recordHandleImport = false;
            int recordHandleImportStepTime = 0;
            /**
             * ========  如果 没有未导入与导入失败的则无需重试 2=======
             */
            while (recordHandleImportStepTime < IMPORT_TASK_AND_STEP_RETRY_TIME && !recordHandleImport) {
                recordHandleImport = importDataStepDao.queryTodayStepIsImportSuccess(day) == 0;
                if (recordHandleImport) {
                    logger.info("import_x_x_x 该数据已导入完毕2 ====!");
                } else {
//                    highImportDataService.recordHandleImport(day);
                    List<ImportDataTask> importDataTasks = importDataTaskDao.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
                    logger.info("每天需要处理 importDataTasks size : {}", importDataTasks.size());

                    final CountDownLatch latch = new CountDownLatch(importDataTasks.size());

                    for (ImportDataTask importDataTask : importDataTasks) {

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    /**
                                     * 返回结果失败结果重试10次
                                     */
                                    final Integer RetryTime = 10;
                                    int handleTime = 0;
                                    while (handleTime < RetryTime) {
                                        List<ImportDataStep> steps = importDataStepDao.queryAllStepNotDealAndFail(importDataTask.getId(),
                                                day, Constant.IMPORTTYPE.point.name());
                                        if (CollectionUtils.isEmpty(steps)) {
                                            break;
                                        }
                                        logger.info("point handletime 2 重试!,{}", handleTime);
                                        highImportDataService.insertPointTaskStep(steps);
                                        handleTime++;
                                    }
                                } finally {
                                    latch.countDown();
                                }

                            }
                        });
                    }
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        //被打断的可能性很小
                        logger.error("当前线程被打断！", e);
                    }
                }
            }
        } else {
            logger.info("task and step 未导入！");
            return;
        }
        long end = System.currentTimeMillis();
        logger.info("导入生成全部 task .step import_x_x_x 需要时间 ！time  = {}", end - start);
    }


}
