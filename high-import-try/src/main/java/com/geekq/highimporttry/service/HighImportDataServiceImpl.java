package com.geekq.highimporttry.service;

import com.geekq.highimporttry.entity.ImportDataStep;
import com.geekq.highimporttry.entity.ImportDataTask;
import com.geekq.highimporttry.entity.Point;
import com.geekq.highimporttry.mapper.ImportDataStepDao;
import com.geekq.highimporttry.mapper.ImportDataTaskDao;
import com.geekq.highimporttry.mapper.ImportPointDao;
import com.geekq.highimporttry.mapper.PointDao;
import com.geekq.highimporttry.util.Constant;
import com.geekq.highimporttry.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 邱润泽 bullock
 */
@Service
public class HighImportDataServiceImpl implements HighImportDataService {

    public static final Logger logger = LoggerFactory.getLogger(HighImportDataServiceImpl.class);

    @Autowired
    private PointDao pointDao;

    @Autowired
    private ImportDataTaskDao importDataTaskDao;

    @Autowired
    private ImportDataStepDao importDataStepDao;

    @Autowired
    private ImportPointDao importPointDao;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Override
    public void recordHandle(String day) {

        System.out.println("111111");
        ImportDataTask pointTask = this.queryPointTask(day);
        List<ImportDataStep> pointSteps = this.queryPointSteps(pointTask);

        /**
         * 保证数据一致性
         */
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        try {
            /**
             * 插入 task表
             */
            importDataTaskDao.insert(pointTask);

            /**
             * 插入step 表
             */
            for (ImportDataStep stat : pointSteps) {
                stat.setTaskId(pointTask.getId());
            }
            importDataStepDao.insertBatch(pointSteps);
            transactionManager.commit(transaction);
        } catch (Exception e) {
            logger.error("数据插入失败！！回滚！", e);
            transactionManager.rollback(transaction);
        }
    }

    @Override
    public void recordHandleImport(String day) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            BillRecordPointInsertTask pointInsertTask = new BillRecordPointInsertTask(importDataTaskDao, importDataStepDao,
                    day, this);
            executor.execute(pointInsertTask);
        } catch (Exception e) {
            logger.error("导入数据 import_x_x 线程池发生错误", e);
        }
        executor.shutdown();
        try {
            while (!executor.isTerminated()) {
                if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.info("recordHandleImport thread pool close successfully");
                } else {
                    logger.info("Waiting recordHandleImport thread pool close...");
                }
            }
        } catch (InterruptedException e) {
            logger.error("线程池异常", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
            executor.shutdownNow();
        }

    }


    /**
     * ===============================   查询 point 参数拼接 =========================
     */

    public ImportDataTask queryPointTask(String day) {
        logger.info("start -------- > point 拼接task!");
        ImportDataTask record = new ImportDataTask();
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setDay(day);
        record.setStatus(Constant.IMPORT_NOT_DEAL);
        record.setType(Constant.IMPORTTYPE.point.name());
        record.setVersion(1);
        return record;
    }

    public List<ImportDataStep> queryPointSteps(ImportDataTask importDataTask) {
        long maxPointId = pointDao.getMaxPointId();
        logger.info("start -------->> pointSteps拼接开始!");
        long index;
        if (maxPointId % Constant.limitNew != 0) {
            index = maxPointId / Constant.limitNew + 1;
        } else {
            index = maxPointId / Constant.limitNew;
        }
        List<ImportDataStep> lists = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            ImportDataStep step = new ImportDataStep();
            step.setCreateTime(new Date());
            step.setDay(importDataTask.getDay());
            //mysql between 去除 重复数据
            if (i == 0) {
                step.setRangeStart((long) i * Constant.limitNew);
            } else {
                step.setRangeStart((long) i * Constant.limitNew + 1);
            }
            //获取 最后一条
            if (i == index - 1) {
                step.setRangeEnd(maxPointId);
            } else {
                step.setRangeEnd((long) (i + 1) * Constant.limitNew);
            }
            step.setMsg("point 表数据 index :" + index + "起始位置" + step.getRangeStart() + "结束位置" + step.getRangeEnd() + "天数" + importDataTask.getDay());
            step.setTaskId(importDataTask.getId());
            step.setVersion(1);
            step.setUpdateTime(new Date());
            step.setStatus(Constant.IMPORT_NOT_DEAL);
            step.setType(Constant.IMPORTTYPE.point.name());
            lists.add(step);
        }
        return lists;
    }


    /**
     * ==============================================
     *
     * @param steps
     */
    @Override
    public void insertPointTaskStep(List<ImportDataStep> steps) {
        //返回结果
        for (ImportDataStep step : steps) {
            try {
                logger.info("start  账单 point 批量插入处理！ " + step.getRangeStart() + " end :" + step.getRangeEnd());
                List<Point> points = pointDao.queryAllByPointId(step.getRangeStart(), step.getRangeEnd());
                String yMonth = DateUtil.formatCompactMonthDate(DateUtil.parse(step.getDay()));
                /**
                 * 事务保证批次和失败成功状态对应上
                 */
                DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
                TransactionStatus transaction = transactionManager.getTransaction(definition);
                try {
                    if (!CollectionUtils.isEmpty(points)) {
                        logger.info("start insert batch !");
                        int flag = importPointDao.insertBatch(points, "import_point_" +
                                yMonth, step.getDay(), new Date());
                        if (flag > 0) {
                            importDataStepDao.updateByStepId(step.getId(), Constant.IMPORT_SUCCESS, new Date());
                            importDataTaskDao.updateByTaskId(step.getTaskId(), Constant.IMPORT_SUCCESS, new Date());
                        } else {
                            importDataStepDao.updateByStepId(step.getId(), Constant.IMPORT_FAIL, new Date());
                            importDataTaskDao.updateByTaskId(step.getTaskId(), Constant.IMPORT_FAIL, new Date());
                        }
                    } else {
                        importDataStepDao.updateByStepId(step.getId(), Constant.IMPORT_RANGE_NO_DATA, new Date());
                        importDataTaskDao.updateByTaskId(step.getTaskId(), Constant.IMPORT_RANGE_NO_DATA, new Date());
                        logger.info("point  start" + step.getRangeStart() + "end :" + step.getRangeEnd() + "+ 范围无数据！！");
                    }
                    transactionManager.commit(transaction);
                } catch (Exception e) {
                    logger.error("数据插入失败！！回滚！", e);
                    transactionManager.rollback(transaction);
                }
                logger.info("start  " + step.getRangeStart() + " end :" + step.getRangeEnd());
            } catch (Exception e) {
                logger.error("数据插入失败！！回滚！此次批次失败的start! {},{},继续下一批次start ", step.getRangeStart(), e);
            }
        }
    }


}
