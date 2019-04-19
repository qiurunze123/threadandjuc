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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author 邱润泽 bullock
 *
 * 栅栏方式 --- 写的我脑袋大了
 */
@Service
public class TimerRunner3 {

    public static final Logger logger = LoggerFactory.getLogger(TimerRunner3.class);
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


        logger.info("billNewRunnerImport start 导入step  and task 数据开始 3 ！");
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
                logger.info("今日{}数据已导入importdataTask 和 importdataStep 表 3", day);
            } else {
                highImportDataService.recordHandle(day);
            }
            flagImport = todayIsTryImport;
            importTaskAndStepTime++;
        }

        List<ImportDataTask> importDataTasks = importDataTaskDao.queryTaskDatas(Constant.IMPORTTYPE.point.name(), day);
        logger.info("每天需要处理 importDataTasks size 3: {}", importDataTasks.size());
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(importDataTasks.size());

        Thread[] allSoldier = new Thread[importDataTasks.size()];
        /**
         * 插入了BarrierRun作为barrierAction
         */
        for (int i = 0; i < importDataTasks.size(); i++) {
            System.out.println("士兵 " + i + " 报道");
            allSoldier[i] = new Thread(new ImportThread(cyclicBarrier,
                    importDataStepDao, importDataTasks.get(i), highImportDataService, day));
            allSoldier[i].start();
        }
        long end = System.currentTimeMillis();
        logger.info("导入生成全部 task .step import_x_x_x 需要时间 ！time  = {}", end - start);
    }


    public static class ImportThread implements Runnable {

        private ImportDataStepDao importDataStepDao;

        private ImportDataTask importDataTask;

        private final CyclicBarrier cyclic;

        private HighImportDataService highImportDataService;

        private String day;

        public ImportThread(CyclicBarrier cyclic, ImportDataStepDao importDataStepDao,
                            ImportDataTask importDataTask, HighImportDataService highImportDataService, String day) {
            this.importDataStepDao = importDataStepDao;
            this.cyclic = cyclic;
            this.highImportDataService = highImportDataService;
            this.importDataTask = importDataTask;
            this.day = day;
        }

        /**
         * CyclicBarrier的核心方法就只有一个await，它会抛出两个异常，InterruptedException和BrokenBarrierException。
         * InterruptedException显然是当前线程等待的过程被中断而抛出的异常，而这些要集合的线程有一个线程被中断就会导致线程永远都无法集齐，
         * 导致“栅栏损坏”，剩下的线程就会抛出BrokenBarrierException异常
         */
        @Override
        public void run() {
            try {
                logger.info("等待线程集合完毕！");
                /**
                 * 返回结果失败结果重试10次
                 */
                List<ImportDataStep> steps = importDataStepDao.queryAllStepNotDealAndFail(importDataTask.getId(),
                        day, Constant.IMPORTTYPE.point.name());
                highImportDataService.insertPointTaskStep(steps);
                cyclic.await();
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
                logger.info("撩骚！");
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
